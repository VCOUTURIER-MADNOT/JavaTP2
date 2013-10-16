import java.io.IOException;

import server.RequestHandler;
import server.TCPServer;
import server.UDPServer;

public class ServerMain {
	
	public static void main(String[] args)
	{
		RequestHandler requestHandler = new RequestHandler();
		
		try {
			TCPServer tcpServ = new TCPServer(requestHandler, 51510);
			UDPServer udpServ = new UDPServer(requestHandler, 51511);
			
			System.out.println("Servers online");
			tcpServ.start();
			udpServ.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
