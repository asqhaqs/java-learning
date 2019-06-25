package start;

import rpc.RpcProvider;
import service.BatterCakeServiceImpl;
import service.impl.BatterCakeService;

/**
 * Create by xudong
 * Author: xudong
 * Date: 2019-06-25
 */
public class RpcBootStrap {
    public static void main(String[] args) throws Exception{
        BatterCakeService batterCakeService = new BatterCakeServiceImpl();
        //发布服务， 注册在 20006端口
        RpcProvider.export(20006, batterCakeService);
    }
}
