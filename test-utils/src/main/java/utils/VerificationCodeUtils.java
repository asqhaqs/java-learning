package utils;

import java.util.Random;

/**
 * @author xudong03 <xudong03@kuaishou.com>
 * Created on 2023-02-13
 */
public class VerificationCodeUtils {
    private static final String ALL_NUMBERS = "0123456789";
    private static final Integer DEFAULT_LENGTH = 6;

    public static String generateNumVerificationCode() {
        StringBuilder sb = new StringBuilder(DEFAULT_LENGTH);
        for(int i=0; i < DEFAULT_LENGTH; i++) {
            char ch = ALL_NUMBERS.charAt(new Random().nextInt(DEFAULT_LENGTH));
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int j = 0;
        while (j < 20) {
            System.out.println(generateNumVerificationCode());
            j++;
        }
    }
}
