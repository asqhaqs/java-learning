package com.brianway.learning.java.base;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2021-07-28
 */
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 签名算法
 */
public class Test {

    /**
     * 将md5转成16进制字符串
     */
    public static String covertMd5ToHexString(byte[] md5) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < md5.length; i++) {
            byte b = md5[i];
            int high = ((b & 0x00f0) >> 4);
            int low = (b & 0x000f);
            if (high >= 10) {
                sb.append((char) (high - 10 + 'a'));
            } else {
                sb.append((char) (high + '0'));
            }
            if (low >= 10) {
                sb.append((char) (low - 10 + 'a'));
            } else {
                sb.append((char) (low + '0'));
            }
        }
        return sb.toString();
    }

    public static byte[] getMd5(byte[] input) {
        if (input == null) {
            input = new byte[0];
        }
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(input);
            // 转换并返回结果，也是字节数组，包含16个元素
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // never be here
            return new byte[16];
        }
    }

    public static byte[] getMd5(String input) {
        return getMd5(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String getMd5HexString(String input) {
        byte[] md5 = getMd5(input);
        return covertMd5ToHexString(md5);
    }

    /**
     * VPS签名算法
     * @param cp 业务方
     * @param token 业务方token
     * @param name 待解析的url，注意是URL编码前的
     * @param ts 毫秒级时间戳
     * @return 返回长32的小写的MD5字符串
     */
    public static String getPubSign(String cp, String token, String name, long ts) {
        String result = String.format("%s`%s`%s`%s", cp, token, String.valueOf(ts), name);
        System.out.println(result);
        return getMd5HexString(result);
    }

    /**
     * 将字符串转换为不重复的userId
     * @param strId 字符串
     * @return userId
     */
    public static Long transStrIdToLong(String strId) {
        try {
            System.out.println(DigestUtils.md5Hex(strId.getBytes()));
            String md5 = DigestUtils.md5Hex(strId.getBytes()).substring(0, 15);
            System.out.println(md5);
            return Long.parseLong(md5, 16);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }

    }

    public static void main(String[] args) {
        //签名示例
//        String cp = "your_cp"; //这是一个示例，调试的时候改为自己的cp
//        String token = "S3YN9WN"; //cp对应的token，鱼塘查看
//        String name = "https://www.bilibili.com/video/av34764072?spm_id_from=333.788##quality=32##";
//        long ts = 1576136754383L; //调试的时候修改为当前时间戳，毫秒级时间戳
//        String sign = getPubSign(cp, token, name, ts); //时效性3min
//        System.out.println(sign); //打印结果: e4ab41e1a2f6f9ab3383c8bc0048c489
        System.out.println(transStrIdToLong("@jefitaaccl7"));
    }

}
