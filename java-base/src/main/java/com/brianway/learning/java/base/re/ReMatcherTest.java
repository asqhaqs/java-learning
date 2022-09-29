package com.brianway.learning.java.base.re;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2022-02-09
 */
public class ReMatcherTest {

    private static final String HEADER_URL_PATTERN = "https://.*/.*\\.webp\\?.*";
    private static final String AVATAR_PRE = "https://bs3-sgp.corp.kuaishou.com/upload-dd-store/dok_avatar_";
//    private static final Pattern HEADER_PATTERN = Pattern.compile(HEADER_URL_PATTERN);

    public static void main(String[] args) {
        String headUrl1 = "https://p19-sign.tiktokcdn-us.com/tos-useast5-avt-0068-tx/6d15e8217e87f742d3ae8dc30910c814~c5_1080x1080.webp?x-expires=1644411600&x-signature=OqmKqH5KOfflvpWf32CqRGGsV1U%3D";
        boolean isMatch1 = Pattern.matches(HEADER_URL_PATTERN, headUrl1);

        String headUrl2 = "https://p16-sign-va.tiktokcdn.com/musically-maliva-obj/1594805258216454~c5_1080x1080.webp?x-expires=1644364800&x-signature=EbjmYADy8dIhff1h2NXEw5lQ0m0%3D";
        boolean isMatch2 = Pattern.matches(HEADER_URL_PATTERN, headUrl2);

        String headUrl3 = "https://p16-sign-sg.tiktokcdn.com/aweme/1080x1080/tos-alisg-avt-0068/fa6fc9002f8a962b9a7fc45f98707eed.webp?x-expires=1644408000&x-signature=0PW3aecafdUD%2FN5F%2F8jQ2ylWgnQ%3D";

        System.out.println("isMatch1: " + isMatch1 + " \n " + "isMatch2: " + isMatch2);
        String url1 = AVATAR_PRE + getS3Url(headUrl1);
        String url2 = AVATAR_PRE + getS3Url(headUrl2);
        String url3 = AVATAR_PRE + getS3Url(headUrl3);

        System.out.println("url1: " + url1);
        System.out.println("url2: " + url2);
        System.out.println("url3: " + url3);
    }

    public static String getS3Url(String headUrl) {
        if (StringUtils.isBlank(headUrl) || !Pattern.matches(HEADER_URL_PATTERN, headUrl)) {
            return "";
        }
        String[] headArray = headUrl.split("/");
        if (headArray.length == 0) {
            return "";
        }
        String endString = headArray[headArray.length -1];
        String keyString = endString.split(".webp?")[0];
        if (keyString.contains("~c5_1080x1080")) {
            return keyString.split("~c5_1080x1080")[0];
        } else {
            return keyString;
        }
    }
}
