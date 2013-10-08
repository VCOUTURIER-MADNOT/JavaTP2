package server;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class API {

	private static Document openUtilisateursXMLFile()
	{
		try{
			// création d'une fabrique de documents
			DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
			
			// création d'un constructeur de documents
			DocumentBuilder constructeur = fabrique.newDocumentBuilder();
			
			// lecture du contenu d'un fichier XML avec DOM
			File xml = new File("utilisateurs.xml");
			Document document = constructeur.parse(xml);
			
			return document;
		
		}catch(ParserConfigurationException pce){
			System.out.println("Erreur de configuration du parseur DOM");
			System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
		}catch(SAXException se){
			System.out.println("Erreur lors du parsing du document");
			System.out.println("lors de l'appel à construteur.parse(xml)");
		}catch(IOException ioe){
			System.out.println("Erreur d'entrée/sortie");
			System.out.println("lors de l'appel à construteur.parse(xml)");
		}
		
		return null;

	}
	
	private static Node getCurrentUserNode(Document document, String username) {
		
		NodeList users = document.getElementsByTagName("login");
		
		for (int i = 0; i < users.getLength(); ++i)
		{
			String user = users.item(i).getFirstChild().getNodeValue();
			if (user.equals(username))
			{
				return users.item(i).getParentNode();
			}
		}
		
		return null;
	}
	
	
	public static Map<String, Map<String, String>> getMethods(boolean isUDP, String connectionString)
	{
		Map<String, Map<String, String>> methods = new HashMap<String, Map<String, String>>();
		
		// Recuperer le nom de l'utilisateur et son mot de passe (format username:password)
		String username = connectionString.split(":")[0];
		String password = connectionString.split(":")[1];
		
		// Recuperer niveau de droit de l'utilisateur grace la balise userLevel qui correspond pour l'utilisateur correspondant
		Document utilisateurs = API.openUtilisateursXMLFile();
		
		Node currentUser = API.getCurrentUserNode(utilisateurs, username);
		
		System.out.println(currentUser.getFirstChild().getNodeValue());
		
		// Recuperer les methodes de la classe API dont l'annotation PROTO , USERLEVEL correspond
		
		return null;
	}
	

}
