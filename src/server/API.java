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
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface AUTHORIZED {
		int userlevel() default 1;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	public @interface DESCRIPTION {
		String menuDesc();
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
			
			String currentUser = crtUser.getChildText("login");
			
			if ( currentUser.equals(username)){
				return crtUser;
			}
			
		}
		return null;
	}
	
	private static boolean checkCredentials(Element currentUser, String username, String password) {
		return currentUser.getChildText("login").equals(username) && currentUser.getChildText("password").equals(password);
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
				Method[] APImethods = (Class.forName("server.API")).getMethods();
				
				for(Method crtMethod : APImethods)
				{

					
					AUTHORIZED annotation = crtMethod.getAnnotation(AUTHORIZED.class);
					if (annotation != null)
					{
						
						if (annotation.userlevel() <= currentUserLevel)
						{
							// Ajout de la methode dans la Map
							Map<String,String> params = new HashMap<String,String>();
							
							params.put("return", crtMethod.getReturnType().getSimpleName());
							
							Class[] paramTypes = crtMethod.getParameterTypes();
							
							for (int i = 0; i < paramTypes.length; i++)
							{
								params.put("param" + i, paramTypes[i].getSimpleName());
							}
							
							
							DESCRIPTION description = crtMethod.getAnnotation(DESCRIPTION.class);
							if (description != null)
							{	
								params.put("menuDesc", description.menuDesc());
							}
							
							methods.put(crtMethod.getName(), params);
						}
					}
				}
				
				return methods ;
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	@AUTHORIZED(userlevel=1)
	@DESCRIPTION(menuDesc="Test Method")
	public static String testMethod(Integer i, String t)
	{
		return i + " : " + t ;
	}

	@AUTHORIZED(userlevel=1)
	public static int connect(String username)
	{
		Element currentUser = getCurrentUserNode(username);
		int currentUserLevel = Integer.parseInt(currentUser.getChildText("userLevel"));
		return currentUserLevel;
	}
	
	
}
