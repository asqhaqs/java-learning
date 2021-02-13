package Utils;

import com.kuaishou.oversea.growth.content.activity.sdk.rpc.client.RRActivityUserQueryClient;
import com.kuaishou.oversea.growth.content.activity.sdk.rpc.client.RRActivityUserQueryClientForTest;
import com.kuaishou.oversea.growth.content.activity.sdk.rpc.domains.RRPQueryResponseInfo;
import com.kuaishou.oversea.growth.content.activity.sdk.rpc.enums.RRActivityRequestType;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2020-11-24
 */
public class RRPTestClient {
    public static void main(String[] args) {

        // 测试用client
        RRPQueryResponseInfo rrpQueryResponseInfo =
                RRActivityUserQueryClientForTest.queryRRActivityUserResponse(
                        111111L, "iphone", RRActivityRequestType.POP_UP);
        System.out.println(rrpQueryResponseInfo.getIsTargetUser());
        System.out.println(rrpQueryResponseInfo.getMoneyAmount());


        RRPQueryResponseInfo rrpQueryResponseInfo1 =
                RRActivityUserQueryClientForTest.queryRRActivityUserResponse(
                        111111L, "iphone", RRActivityRequestType.RECEIVE_MONEY);

        // 正式用client（server还没准备好）
        RRPQueryResponseInfo rrpQueryResponseInfo2 =
                RRActivityUserQueryClient.queryRRActivityUserResponse(
                        111111L, "iphone", RRActivityRequestType.RECEIVE_MONEY);

    }
}
