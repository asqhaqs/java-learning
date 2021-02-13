package Utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2020-12-09
 */
public class UrlUtils {
    /**
     * 将http 链接里的host 跟换
     *
     * @param url http://sjymov.a.yximgs
     * .com/upic/2020/12/09/17/BMjAyMDEyMDkxNzM2MjNfMTI3NTgwMTQyNF80MDQ1MTIxMTE5OF8wXzM
     * =_b_B657db5720fe4e1144afd960e0510983f.mp4?tag=1-1607515845-nil-0-sitd0j1azy-6c37b36bb6b7861f
     * @param host cdnfile.corp.kuaishou.com
     */
    public static String changeHost(String url, String host) {
        if (StringUtils.isEmpty(url) || StringUtils.isEmpty(host)) {
            return "";
        }
        String restUrl = null;
        if (url.startsWith("http://")) {
            restUrl = url.substring(7);
            int index = restUrl.indexOf("/");
            return "http://" + host + restUrl.substring(index);
        } else if (url.startsWith("https://")) {
            restUrl = url.substring(8);
            int index = restUrl.indexOf("/");
            return "https://" + host + restUrl.substring(index);
        }
        return "";

    }

    public static void main(String[] args) {
        String chageUrl = changeHost("https://sjymov.a.yximgs.com/upic/2020/12/09/17/BMjAyMDEyMDkxNzM2MjNfMTI3NTgwMTQyNF80MDQ1MTIxMTE5OF8wXzM=_b_B657db5720fe4e1144afd960e0510983f.mp4?tag=1-1607515845-nil-0-sitd0j1azy-6c37b36bb6b7861f",
                "cdnfile.corp.kuaishou.com");
        System.out.println(chageUrl);
    }

}
