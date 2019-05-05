import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import coder.WebFlowLogGatherMsgBinCoder;
import coder.WebFlowLogGatherMsgCoder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AESUtil;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-05-05
 */
public class KafkaConsumerManager implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerManager.class);

    private final String topic;

    private KafkaConsumer<String, Object> consumer;

    private  AESUtil aESUtil = null;

    // （2）sensor protocol
    private final static WebFlowLogGatherMsgCoder webFlowLogGatherMsgCoder = new WebFlowLogGatherMsgBinCoder();

    private Integer logNumber = 0;
    private Date endtime;

    public KafkaConsumerManager(String topic, String endtime){
        logger.warn(String.format("[info: init KafkaConsumerManager[%s]]", topic));
        this.topic = topic;
        this.consumer = new KafkaConsumer<String, Object>(createConsumerConfig());
        aESUtil = new AESUtil();
        aESUtil.init_aes(SystemConstants.FILE_PATH + "/decrypt.conf");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.endtime = df.parse(endtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Integer showNumber(){
        System.out.println("the logNumber of " + Thread.currentThread() + "is: " + this.logNumber);
        return this.logNumber;
    }


    private static Properties createConsumerConfig(){
         logger.info("init createConsumerConfig");
         Properties properties = new Properties();
         properties.put("bootstrap.servers", SystemConstants.BROKER_URL);
         properties.put("group.id", SystemConstants.KAFKA_CONSUMER_TEST_ID_GYWA);
         properties.put("enable.auto.commit", "false");
         properties.put("auto.commit.interval.ms", "1000");
         properties.put("auto.offset.reset", SystemConstants.KAFKA_CONSUMER_AUTO_OFFSET_RESET_GYWA3061);
         properties.put("session.timeout.ms", "30000");
         properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
         properties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
         properties.put("partition.assignment.strategy", "org.apache.kafka.clients.consumer.RangeAssignor");
         properties.put("max.partition.fetch.bytes", "10485760");
         // kerberos 授权&认证
         if (SystemConstants.IS_KERBEROS.equals("true")) {
             properties.put("security.protocol", "SASL_PLAINTEXT");
             properties.put("sasl.kerberos.service.name", "kafka");
         }

         return properties;
    }

    public void run(){
        try{
            this.consumer.subscribe(Arrays.asList(topic));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (true) {
                ConsumerRecords<String, Object> records = consumer.poll(100);
                for(ConsumerRecord<String, Object> record : records){
                    logger.debug("topic = %s, partition = %s, offset = %d", record.topic(), record.partition(),
                            record.offset());
                    byte[] skyeyeWebFlowLogByteArray = (byte[]) record.value();
                    List<Object> pbBytesWebFlowLogList = webFlowLogGatherMsgCoder.fromWire(skyeyeWebFlowLogByteArray);
                    this.logNumber += pbBytesWebFlowLogList.size();
                }
                Date nowdate = new Date();
                int va = nowdate.compareTo(endtime);
                if(va > 0){
                    System.out.println("end time is: " + df.format(nowdate));
                    showNumber();
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
