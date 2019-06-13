/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-05-05
 */
public class kafkaConsumerForCount {

    public static void main(String[] args) {

        String[] topicList = SystemConstants.TOPIC_COBSUMER.split(",");
        String[] methodList = SystemConstants.METHOD.split(",");
        for(int i =0; i < topicList.length; i++){
            KafkaConsumerManager consumerManager = new KafkaConsumerManager(topicList[i], methodList[i], SystemConstants.END_TIME);
            Thread consumer1 = new Thread(consumerManager);
            consumer1.start();
        }

    }
}
