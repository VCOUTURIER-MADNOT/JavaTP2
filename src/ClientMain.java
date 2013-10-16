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
			
			TCPConnection tcpCo = new TCPConnection();
			
			// Recuperation du userLevel
			tcpCo.write(util.Serialization.MethodToXML(connectionString, "connect", username));
			
			Integer userLevel = (Integer)builder.fromXML(tcpCo.readData());
			Client c;
			if (userLevel > 1)
			{
				c = new Client(connectionString, true, new UDPConnection());
			}
			else 
			{
				c = new Client(connectionString, false, new TCPConnection());
			}
			c.launch();
			System.out.println(userLevel);

		} catch (IOException e) {
			System.out.println("Couple utilisateur / mot de passe incorrect ou accès interdit");
		}
	}
}
