package utils;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class AESUtil {

    public static LgetLib INSTANCE = null;// = (LgetLib) Native.loadLibrary("aes", LgetLib.class);
    static {
        // System.load("libaes.so");
        // 调用linux下面的so文件,注意，这里只要写test就可以了，不要写libtest，也不要加后缀
        INSTANCE = (LgetLib) Native.loadLibrary("aes", LgetLib.class);
    }

    public interface LgetLib extends Library {
        int init_aes(String file);
        int encrypt(byte[] data_buff, int data_len, byte[] encrypt_buff);
        int decrypt(byte[] data_buff, int data_len, byte[] decrypt_buff);
    }

    public int init_aes(String file) {
        return INSTANCE.init_aes(file);
    }

    public int encrypt(byte[] data_buff, int data_len, byte[] encrypt_buff) {
        return INSTANCE.encrypt(data_buff, data_len, encrypt_buff);
    }

    public int decrypt(byte[] data_buff, int data_len, byte[] decrypt_buff) {
        return INSTANCE.decrypt(data_buff, data_len, decrypt_buff);
    }
}
