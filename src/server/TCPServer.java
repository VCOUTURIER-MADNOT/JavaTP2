package server;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer extends Thread {

	private RequestHandler rh;
	private ServerSocket socketServ;
	
	public TCPServer(RequestHandler _rh, int port) throws IOException
	{
		rh = _rh;
		socketServ = new ServerSocket(port);
	}
	
	public void run()
	{
		while(true)
			try {
				rh.register(socketServ.accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
