package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer extends Thread {

	private RequestHandler rh;
	private DatagramSocket ds;
	
	public UDPServer(RequestHandler _rh, int port) throws IOException
	{
		rh = _rh;
		ds = new DatagramSocket(port,InetAddress.getLocalHost());
	}
	
	public void run()
	{
		while(true)
		{
			byte[] byteToPacket = new byte[1024];
			
			DatagramPacket dp = new DatagramPacket(byteToPacket, byteToPacket.length);
			
			try {
				ds.receive(dp);
				rh.register(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
