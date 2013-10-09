package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import server.API;

public class Serialization {

	private static final String HEX_DIGITS = "0123456789abcdef";
	
	// Traitement du XML
	public static MethodParam XMLToMethod(String _xmlString) {
		SAXBuilder sxb = new SAXBuilder();
		try {
			Document doc = sxb.build(_xmlString);
			Element racine = doc.getRootElement();
			
			String nomMethode = racine.getAttributeValue("method");
			ArrayList<Class> paramsType = new ArrayList<Class>();
			ArrayList<String> paramsValue = new ArrayList<String>();
			
			for(Element param : racine.getChildren("param"))
			{
				paramsType.add(Class.forName(param.getAttributeValue("type")));
				paramsValue.add(param.getText());
			}
			Method method = API.class.getMethod(nomMethode, (Class[]) paramsType.toArray());
			MethodParam methodParam = new MethodParam(method, paramsValue);
			
			return methodParam;
		} catch (JDOMException | IOException | ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ResultToXML(Object result)
	{
		//TODO
		Element racine = new Element("answer");

		Document doc = new Document(racine);
		
		return null;
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
