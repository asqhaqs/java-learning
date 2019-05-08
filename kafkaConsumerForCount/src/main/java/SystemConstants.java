import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 *
 * @author wangbin03
 *
 */
public class SystemConstants extends ConfigurableContants {

    private static final Logger logger = LoggerFactory.getLogger(SystemConstants.class);

    static {
        logger.info("init");
        // init("/app.properties");
        init("/home/storm/test/ceshi.properties");
    }

    public static final String ZOOKEEPER_HOSTS = getProperty("zookeeper_hosts", "m1.gycs.bigdata01.cn:2181,m2.gycs.bigdata01.cn:2181,m3.gycs.bigdata01.cn:2181");
    public static final String BROKER_URL = getProperty("broker_url", "m4.gycs.bigdata01.cn:9092,m5.gycs.bigdata01.cn:9092,m7.gycs.bigdata01.cn:9092");

    public static final String KAFKA_SPOUT_THREADS = getProperty("kafka_spout_threads", "5");

    public static final String FILE_PATH = getProperty("file_path", "/home/storm/geoipdata");
    // debug
    public static final String DEBUG = getProperty("debug", "true");

    // encrypt
    public static final String WEBFLOW_LOG_ENCRYPT = getProperty("encrypt", "true");
    public static final String WEBFLOW_LOG_ENCRYPT_BUFFER_SIZE = getProperty("encrypt_buffer_size", "100000");

    // nest record: Record--Array--Record
    public static final String RECORD_ARRAY_RECORD = getProperty("record_array_record", "skyeye_ssl:cert,skyeye_mail:attachment");


    // Kerberos 授权&认证
    public static final String IS_KERBEROS = getProperty("is_kerberos", "true");
    public static final String KAFKA_KERBEROS_PATH = getProperty("kafka_kerberos_path", "/home/storm/geoipdata");
    static {
        if (SystemConstants.IS_KERBEROS.equals("true")) {
            System.setProperty("java.security.auth.login.config",
                    SystemConstants.KAFKA_KERBEROS_PATH + File.separator + "kafka_server_jaas.conf");
            System.setProperty("java.security.krb5.conf", SystemConstants.KAFKA_KERBEROS_PATH + File.separator + "krb5.conf");
        }
    }

    // # kafka consumer group id
    public static final String KAFKA_CONSUMER_TEST_ID_GYWA = getProperty("kafka_consumer_test_id_gywa", "kafka_consumer_test_id_gywa");

    // # auto.offset.reset String must be one of: latest, earliest
    public static final String KAFKA_CONSUMER_AUTO_OFFSET_RESET_GYWA3061 = getProperty("kafka_consumer_auto_offset_reset_gywa3061",
            "earliest");

    public static final String METHOD = getProperty("topic_to_method","getSkyeyeIds");
    public static final String END_TIME = getProperty("endtime", "2019-05-06 14:30:00");
    public static final String TOPIC_COBSUMER = getProperty("topic_consumer", "td_skyeye_file");

    public static final String MARKED_FIELD = getProperty("markedfield","rule_id");
    public static final String MARKED_VALUE = getProperty("markedvalue","51206");

    public static void main(String[] args) {
        System.out.println("SystemConstants.KAFKA_SPOUT_THREADS: " + SystemConstants.KAFKA_SPOUT_THREADS);

        System.out.println("SystemConstants.KAFKA_CONSUMER_AUTO_OFFSET_RESET_GYWA3061: " +
                SystemConstants.KAFKA_CONSUMER_AUTO_OFFSET_RESET_GYWA3061);
    }

}
