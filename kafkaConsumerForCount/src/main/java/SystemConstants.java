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
        init("/home/storm/test/app.properties");
    }

    public static final String ZOOKEEPER_HOSTS = getProperty("zookeeper_hosts", "127.0.0.1:2181");
    public static final String BROKER_URL = getProperty("broker_url", "127.0.0.1:9092");

    public static final String KAFKA_SPOUT_THREADS = getProperty("kafka_spout_threads", "5");

    public static final String FILE_PATH = getProperty("file_path", "/home/storm/geoipdata");
    // debug
    public static final String TOPOLOGY_DEBUG = getProperty("debug", "true");

    // encrypt
    public static final String WEBFLOW_LOG_ENCRYPT = getProperty("encrypt", "false");
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

    public static void main(String[] args) {
        System.out.println("SystemConstants.KAFKA_SPOUT_THREADS: " + SystemConstants.KAFKA_SPOUT_THREADS);

        System.out.println("SystemConstants.KAFKA_CONSUMER_AUTO_OFFSET_RESET_GYWA3061: " +
                SystemConstants.KAFKA_CONSUMER_AUTO_OFFSET_RESET_GYWA3061);
    }

}
