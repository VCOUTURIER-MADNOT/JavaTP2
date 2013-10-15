package util;

import java.security.NoSuchAlgorithmException;

public class Crypto {
    
    public static String toMD5(String _textToHash) {
    	java.security.MessageDigest msgDigest;
    	String hash = "";
    	
		try {
			msgDigest = java.security.MessageDigest.getInstance("MD5");
	        msgDigest.update(util.Serialization.StringToByteArray(_textToHash));
	        byte[] digest = msgDigest.digest();
	        hash = util.Serialization.ByteArrayToHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        
        return hash;
    }
	
}
