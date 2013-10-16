package util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import server.API;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class Serialization {

	
	private static final String HEX_DIGITS = "0123456789abcdef";
	
	public static String getConnectionString(String _xmlString)
	{
		String connectionString = "";
		SAXBuilder sxb = new SAXBuilder();
		try {
			
			InputStream stream = new ByteArrayInputStream(_xmlString.getBytes("UTF-8"));

			Document doc = sxb.build(stream);
			Element racine = doc.getRootElement();
			
			connectionString = racine.getAttributeValue("connectionString");
			
		}catch (JDOMException | IOException | SecurityException e) {
			e.printStackTrace();
		}
		return connectionString;
	}
	
	// Traitement du XML
	public static MethodInvoker XMLToMethod(String _xmlString) {
		SAXBuilder sxb = new SAXBuilder();
		try {
			
			InputStream stream = new ByteArrayInputStream(_xmlString.getBytes("UTF-8"));

			Document doc = sxb.build(stream);
			Element racine = doc.getRootElement();
			
			String nomMethode = racine.getAttributeValue("method");
			
			ArrayList<Class> paramsType = new ArrayList<Class>();
			ArrayList<Object> paramsValue = new ArrayList<Object>();
			
			for(Element param : racine.getChildren())
			{
				Class c = Class.forName(param.getName());
				paramsType.add(c);

				XStream builder = new XStream(new StaxDriver());
				builder.alias(param.getName(), c);
				Object obj = builder.fromXML("<?xml version=\"1.0\"?><"+param.getName()+">"+param.getText()+"</"+param.getName()+">");
				
				paramsValue.add(obj);
				
				
			}
			
			Class[] classes = new Class[paramsType.size()];
			for (int i=0; i<paramsType.size(); i++)
			{
				classes[i] = paramsType.get(i);
			}
			
			Method method = API.class.getMethod(nomMethode, classes);
			MethodInvoker methodParam = new MethodInvoker(method, paramsValue.toArray());
			
			return methodParam;
		} catch (JDOMException | IOException | ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String ResultToXML(Object result)
	{
		XStream builder = new XStream(new StaxDriver());
		return builder.toXML(result);
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
			return new String(_byteArray, "UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
	
    public static String MethodToXML(String connectionString, String methodName, Object ... params)
    {
		String resultXML =  "<action connectionString=\""+ connectionString +"\" method=\""+methodName+"\">";
		
		for (int i = 0; i < params.length; i++)
		{
			resultXML += "<" + params[i].getClass().getName() + ">" + params[i] + "</" + params[i].getClass().getName() + ">";
		}
		
		resultXML += "</action>";
		return resultXML;
    }
}
