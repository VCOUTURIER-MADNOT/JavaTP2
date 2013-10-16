package network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPConnection implements Connection {
 
	private int port = 51511;


	private DatagramSocket s = null;
 
    public UDPConnection() throws IOException {
        this.s = new DatagramSocket();
    }


	public String readData() throws IOException{
		byte[] bytesFromSocket = new byte[1024];
		String msg;
		
		DatagramPacket packet = new DatagramPacket(bytesFromSocket, bytesFromSocket.length);

		this.s.receive(packet);
		msg = new String(bytesFromSocket, 0, bytesFromSocket.length).trim();
		
		
	 	if(msg.startsWith("<error>"))
	 		throw new IOException("Erreur");
	 	
	 	return msg;
	}
	
	public void write(String str) throws IOException {
		if (str != null || str != ""){
		    DatagramPacket packet = new DatagramPacket(str.getBytes("UTF-8"), str.getBytes("UTF-8").length, InetAddress.getLocalHost(), this.port);
		    this.s.send(packet);
		}
	}

 	public void destroy(){
		this.s.close();
	}
 	
 }
