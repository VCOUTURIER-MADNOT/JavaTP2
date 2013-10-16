package client;

import java.io.IOException;
import java.util.Map;

import network.Connection;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class Client {

	private String connectionString;
	private boolean isUDP;
	private Connection co;
	private XStream builder;
	
	public Client(String _connectionString, boolean _isUDP, Connection _co) {
		this.connectionString = _connectionString;
		this.isUDP = _isUDP;
		this.co = _co;
		this.builder = new XStream(new StaxDriver());
	}

	@SuppressWarnings("unchecked")
	public void launch() {
		// Recuperer liste availableMethods
		try {
			
			co.write(util.Serialization.MethodToXML(this.connectionString, "availableMethods", this.connectionString));
			Map<String, Map<String,String>> methods = (Map<String, Map<String, String>>) builder.fromXML(co.readData());
			
			// Gestion du menu
			
			
			
			// Appel des fonctions
			
			co.destroy();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
