import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import coder.WebFlowLogGatherMsgBinCoder;
import coder.WebFlowLogGatherMsgCoder;
import com.google.protobuf.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import soc.storm.situation.protocolbuffer.AddressBookProtosTDGYWA;
import utils.AESUtil;
import utils.JsonFormatProtocolBuffer;

import org.apache.commons.lang3.StringUtils;
import utils.JsonUtils;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-05-05
 */
public class KafkaConsumerManager implements Runnable{


    private final String topic;

    private KafkaConsumer<String, Object> consumer;

    // 加密解密
    private final static boolean isWebflowLogEncrypt = (SystemConstants.WEBFLOW_LOG_ENCRYPT.equals("true"));
    private  static AESUtil aESUtil = null;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        aESUtil = new AESUtil();
        aESUtil.init_aes(SystemConstants.FILE_PATH + "/decrypt.conf");
    }

    // （2）sensor protocol
    private final static WebFlowLogGatherMsgCoder webFlowLogGatherMsgCoder = new WebFlowLogGatherMsgBinCoder();
    private String topicMethod;// = "getSkyeyeTcpflow";
    private Method getSkyeyeWebFlowLogObjectMethod;
    private final static Class<?> skyeyeWebFlowLogClass = AddressBookProtosTDGYWA.SENSOR_LOG.class;

    private Integer logNumber = 0;
    private Integer markedNumber = 0;
    private Date endtime;

    private byte[] skyeyeWebFlowLogByteArrayElementBytesDest = null;

    public KafkaConsumerManager(String topic, String method, String endtime){

        System.out.println(String.format("[info: init KafkaConsumerManager[%s]]", topic));
        this.topic = topic;
        this.consumer = new KafkaConsumer<String, Object>(createConsumerConfig());
        skyeyeWebFlowLogByteArrayElementBytesDest = new byte[Integer.parseInt(SystemConstants.WEBFLOW_LOG_ENCRYPT_BUFFER_SIZE)];// 61858764、10000
        topicMethod = method;
        try {
            getSkyeyeWebFlowLogObjectMethod = skyeyeWebFlowLogClass.getMethod(topicMethod);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public Integer showNumber(){

        System.out.println("at time " + this.df.format(new Date()) + " the logNumber of topic " + this.topic + " in "+ Thread.currentThread() + "is: " + this.logNumber);
        System.out.println("the markedNumber is: " + this.markedNumber);
        return this.logNumber;
    }


    private static Properties createConsumerConfig(){
         System.out.println("init createConsumerConfig");
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

    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    public void run(){
        try{
            this.consumer.subscribe(Arrays.asList(topic));
            System.out.println("consumer topic: " + topic +"start time is: " + this.df.format(new Date()));
            while (true) {
                ConsumerRecords<String, Object> records = consumer.poll(100);
                for(ConsumerRecord<String, Object> record : records){

                    byte[] skyeyeWebFlowLogByteArray = (byte[]) record.value();
                    List<Object> pbBytesWebFlowLogList = webFlowLogGatherMsgCoder.fromWire(skyeyeWebFlowLogByteArray);

                    for(Object skyeyeWebFlowLogByteArrayElement : pbBytesWebFlowLogList){
                        byte[] skyeyeWebFlowLogByteArrayElementBytes = (byte[]) skyeyeWebFlowLogByteArrayElement;

                        // 加密解密
                        if (isWebflowLogEncrypt) {
                            int decryptBytesLength = aESUtil.decrypt(skyeyeWebFlowLogByteArrayElementBytes,
                                    skyeyeWebFlowLogByteArrayElementBytes.length,
                                    skyeyeWebFlowLogByteArrayElementBytesDest);

                            if (decryptBytesLength < 0) {
                                throw new RuntimeException("topic"+ this.topic +"AES decrpty error");
                            }

                            skyeyeWebFlowLogByteArrayElementBytes = subBytes(skyeyeWebFlowLogByteArrayElementBytesDest, 0, decryptBytesLength);
                        }

                        AddressBookProtosTDGYWA.SENSOR_LOG log = AddressBookProtosTDGYWA.SENSOR_LOG.parseFrom(skyeyeWebFlowLogByteArrayElementBytes);
                        Object skyeyeWebFlowLogPB = getSkyeyeWebFlowLogObjectMethod.invoke(log);
                        String skyeyeWebFlowLogStr = JsonFormatProtocolBuffer.printToString((Message) skyeyeWebFlowLogPB);

                        // 查找统计相关的信息
                        if (StringUtils.isNotBlank(skyeyeWebFlowLogStr)) {
                            Map<String, Object> skyeyeWebFlowLog = JsonUtils.jsonToMap(skyeyeWebFlowLogStr);
                            if (null != skyeyeWebFlowLog ) {
                                this.logNumber++ ;
                                if(skyeyeWebFlowLog.get(SystemConstants.MARKED_FIELD).toString().equals(SystemConstants.MARKED_VALUE)){
                                    //统计
                                    if(SystemConstants.DEBUG.equals("true")){
                                        System.out.println(this.topic + " marked + 1: marked field is" + SystemConstants.MARKED_FIELD  +  "  and marked value is " + SystemConstants.MARKED_VALUE);
                                        showNumber();
                                    }
                                    this.markedNumber++;
                                }

                            }
                        }
                    }
                }
                consumer.commitSync();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            consumer.close();
        }
    }
}
