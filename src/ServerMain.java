import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import server.RequestHandler;

public class ServerMain {
	
	public static void main(String[] args)
	{
		
		try {
			
			RequestHandler rh = new RequestHandler();
			
			ServerSocket socketServer = new ServerSocket(51512);
			
			while(true)
				rh.register(socketServer.accept());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		try {
			
			RequestHandler rh = new RequestHandler();
			
			DatagramSocket ds = new DatagramSocket(51511,InetAddress.getLocalHost());
			
			while(true)
			{
				byte[] byteToPacket = new byte[1024];
				
				DatagramPacket dp = new DatagramPacket(byteToPacket, byteToPacket.length);
				
				ds.receive(dp);
				
				rh.register(dp);
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
	}
}
