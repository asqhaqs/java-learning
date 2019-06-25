package start;

import rpc.RpcConsumer;
import service.BatterCakeService;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-06-25
 */
public class RpcTest {
    public static void main(String[] args) {
        BatterCakeService batterCakeService = RpcConsumer.getService(BatterCakeService.class, "127.0.0.1", 20006);
        String result = batterCakeService.sellBatterCake("牛肉");
        System.out.println(result);
    }
}
