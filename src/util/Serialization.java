package util;

import java.io.UnsupportedEncodingException;

public class Serialization {

	private static final String HEX_DIGITS = "0123456789abcdef";
	
	// Traitement du XML
	public static void XMLToMethod(String _xmlString) {
	}
	
	public static void ResultToXML(Object result)
	{
		
	}
	
	
	public static byte[] StringToByteArray(String _xmlString)
	{
		try {
			return _xmlString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
    public static String ByteArrayToHexString(byte[] _byteArray) {
    	 
        StringBuffer sb = new StringBuffer(_byteArray.length * 2);
        for (int i = 0; i < _byteArray.length; i++) {
            int b = _byteArray[i] & 0xFF;
             sb.append(HEX_DIGITS.charAt(b >>> 4))
               .append(HEX_DIGITS.charAt(b & 0xF));
        }
        return sb.toString();
    }
    
    public static String ByteArrayToXMLString(byte[] _byteArray) {
    	try {
			return new String(_byteArray, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
	
}
