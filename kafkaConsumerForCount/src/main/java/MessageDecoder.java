import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AESUtil;
/**
 * 解码探针流量包
 * @author yesheng
 *
 */
public class MessageDecoder {
	
	private final static Logger logger = LoggerFactory.getLogger(MessageDecoder.class);
  
	/**
	 * 探针日志解包，一个包可能包含多条日志
	 * @param input 原始日志byte数组
	 * @return 分解后的多个日志，未做解密
	 */
    @SuppressWarnings("unchecked")
	public static List<byte[]> getBlocks(byte[] input){
    	if (input==null||input.length<8)
    		return Collections.EMPTY_LIST;
    	
    	int offset = 0;
    	int count = byte2Int(input,offset);
    			
    	if (count<0||count>input.length/4) {   
    		StringBuffer sb = new StringBuffer();
    		for(int i=0;i<input.length&i<64;i++)
    			sb.append(Integer.toHexString((input[i]&0xff)) + " ");
    		logger.warn("Invalid message pack, count = {}, message = [{}...]",count,sb.toString());
    		count = 0;
    	}  	
    		
    	List<byte[]> list = new ArrayList<>(count);
    	
    	offset += 4;
    	int len = 0;
    	for(int i=0;i<count;i++) {
    		if (offset+4>input.length) {
    			break;
    		}
        	len = byte2Int(input,offset); 
        	offset += 4;
        	if (len+offset > input.length) {
        		logger.warn("Message pack error" + offset + " + " + len + " > " + input.length);
        		break;
        	}
        	byte[] block = new byte[len];        	
        	System.arraycopy(input, offset, block, 0, len);
        	offset += len;
        	list.add(block);
    	}
    	return list;
    }
	
	/**
	 * 探针日志解包并解密，一个包可能包含多条日志
	 * @param input 原始日志
	 * @param bufferSize 解密使用的buffer大小，大于单条日志大小即可
	 * @param aes 解密对象
	 * @return
	 */
	public static List<byte[]> decryptLog(byte[] input,int bufferSize,AESUtil aes){
		List<byte[]> bt = MessageDecoder.getBlocks(input);
		List<byte[]> blocks = new ArrayList<>(bt.size());
		byte[] data = new byte[bufferSize];		
		for(byte[] b : bt) {
			try {
				int len = aes.decrypt(b, b.length, data);
				byte[] block = new byte[len];
				System.arraycopy(data, 0, block, 0, len);
				blocks.add(block);
			}catch(Exception e) {
				logger.error("Decrypt error",e);
			}
		}		
		return blocks;
	}
	
	private static int byte2Int(byte[] bs, int offset) {
	  	 return (bs[offset]&0xFF) | ((bs[offset+1] & 0xFF)<<8) | ((bs[offset+2] & 0xFF)<<16) | ((bs[offset+3] & 0xFF)<<24);
	}
	 
}
