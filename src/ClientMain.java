import java.io.IOException;
import java.util.Scanner;

import network.TCPConnection;
import network.UDPConnection;
import client.Client;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;


public class ClientMain {
	
	public static void main(String[] args)
	{
		
		// Connection 
		
		try {
			XStream builder = new XStream(new StaxDriver());
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Saisir nom d'utilisateur");
			String username = sc.nextLine();
			System.out.println("Saisir mot de passe");
			String password = sc.nextLine();
			
			String connectionString = username + ":" + util.Crypto.toMD5(password);
			
			TCPConnection tcpCo = TCPConnection.getInstance();
			
			// Recuperation du userLevel
			tcpCo.write(util.Serialization.MethodToXML(connectionString, "connect", username));
			if (!tcpCo.readData().startsWith("<error>"))
			{
				Integer userLevel = (Integer)builder.fromXML(tcpCo.readData());
				Client c;
				if (userLevel > 1)
				{
					c = new Client(connectionString, UDPConnection.getInstance());
				}
				else 
				{
					c = new Client(connectionString, TCPConnection.getInstance());
				}
				System.out.println(userLevel);
			}
			else 
			{
				System.out.println("Couple utilisateur/mot de passe incorrect");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Si uL >1 : Instanciation d'un Client avec UDPConnection
		// Sinon Instanciation d'un Client avec TCPConnection 
		
		// Recuperation du availableMethods
		// Construction du menu
		// Appel des fonctions
	}
}
