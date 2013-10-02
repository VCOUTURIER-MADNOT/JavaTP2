package util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Crypto {
	
	private static final String HEX_DIGITS = "0123456789abcdef";
	 
    private static String toHexString(byte[] v) {
 
        StringBuffer sb = new StringBuffer(v.length * 2);
        for (int i = 0; i < v.length; i++) {
            int b = v[i] & 0xFF;
             sb.append(HEX_DIGITS.charAt(b >>> 4))
               .append(HEX_DIGITS.charAt(b & 0xF));
        }
        return sb.toString();
    }
    
    public static String toMD5(String _textToHash) {
    	java.security.MessageDigest msgDigest;
    	String hash = "";
    	
		try {
			msgDigest = java.security.MessageDigest.getInstance("MD5");
	        msgDigest.update(_textToHash.getBytes("UTF-8"));
	        byte[] digest = msgDigest.digest();
	        hash = toHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        return hash;
    }
	
	
}
