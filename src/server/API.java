package server;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class API {

	// ATTRIBUTES
	
	private static Element rootElement;
	
	public static enum Protocol { ALL, UDP, TCP };
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AUTHORIZED {
		Protocol protocol() default Protocol.TCP;
		int userlevel() default 1;
	}
	
	//
	
	private static boolean openUtilisateursXMLFile()
	{
		if (API.rootElement == null){
		  SAXBuilder sxb = new SAXBuilder();
	      try
	      {
	         Document document = sxb.build(new File("utilisateurs.xml"));
		     API.rootElement = document.getRootElement();
		     return true;
	      }
	      catch(Exception e){
	    	  e.printStackTrace();
	      }
	      return false;
		}
		return true;
	}
	
	private static Element getCurrentUserNode(String username) {
		
		List<Element> listUsers = API.rootElement.getChildren("user");
		
		Iterator<Element> i = listUsers.iterator();
		while(i.hasNext())
		{
			Element crtUser = (Element)i.next();
			
			if (crtUser.getChildText("login") == username){
				return crtUser;
			}
			
		}
		return null;
	}
	
	private static boolean checkCredentials(Element currentUser, String username, String password) {
		return currentUser.getChildText("login") == username && currentUser.getChildText("password") == password;
	}
	
	
	public static Map<String, Map<String, String>> getMethods(boolean isUDP, String connectionString)
	{
		// Charger le fichier si necessaire ou retourner null 
		if(!API.openUtilisateursXMLFile()){ return null; }
		
		Map<String, Map<String, String>> methods = new HashMap<String, Map<String, String>>();
		
		// Recuperer le nom de l'utilisateur et son mot de passe (format username:password)
		String username = connectionString.split(":")[0];
		String password = connectionString.split(":")[1];
		
		Element currentUser = API.getCurrentUserNode(username);
		
		if (currentUser != null && API.checkCredentials(currentUser, username, password))
		{
			
			// Recuperer niveau de droit de l'utilisateur grace la balise userLevel qui correspond pour l'utilisateur correspondant
			int currentUserLevel = Integer.parseInt(currentUser.getChildText("userLevel"));
			
			// Recuperer les methodes de la classe API dont l'annotation correspond
			
			try {
				Method[] APImethods = (Class.forName("API")).getMethods();
				
				for(Method crtMethod : APImethods)
				{
					AUTHORIZED annotation = crtMethod.getAnnotation(AUTHORIZED.class);
					if (annotation != null)
					{
						if ((annotation.protocol() == Protocol.ALL || annotation.protocol() == (isUDP ? Protocol.UDP : Protocol.TCP)) && annotation.userlevel() <= currentUserLevel)
						{
							// Ajout de la methode dans la Map
							
							Map<String,String> params = new HashMap<String,String>();
							
							params.put("return", crtMethod.getReturnType().getSimpleName());
							
							Class[] paramTypes = crtMethod.getParameterTypes();
							
							for (int i = 0; i < paramTypes.length; i++)
							{
								params.put("param" + i, paramTypes[i].getSimpleName());
							}
							
							methods.put(crtMethod.getName(), params);
							
							return methods ;
						}
					}
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	@AUTHORIZED(protocol=Protocol.ALL, userlevel=1)
	public static void testMethod()
	{
		// Test Method
	}

}
