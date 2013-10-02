package network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPConnection {


    private static volatile UDPConnection instance = null;
 
    
	private String host = "127.0.0.1";
	private int port = 10025;


	private DatagramSocket s = null;
 
    private UDPConnection() throws IOException {
        this.s = new DatagramSocket();
    }
    
    public final static UDPConnection getInstance() throws IOException {
        if (UDPConnection.instance == null) {
           synchronized(UDPConnection.class) {
             if (UDPConnection.instance == null) {
				UDPConnection.instance = new UDPConnection();
             }
           }
        }
        return UDPConnection.instance;
    }


	public String read() throws IOException{
		byte[] bytesFromSocket = new byte[1024];
		String msg;
		
		DatagramPacket packet = new DatagramPacket(bytesFromSocket, bytesFromSocket.length);
		
		this.s.receive(packet);
		msg = new String(bytesFromSocket, 0, bytesFromSocket.length);
		
		return msg;
	}


	public void write(String str) throws IOException {
		if (str != null || str != ""){
		    DatagramPacket packet = new DatagramPacket(str.getBytes(), str.getBytes().length, InetAddress.getByName(this.host), this.port);
		    this.s.send(packet);
		}
	}

 	public void destroy(){
		this.s.close();
		UDPConnection.instance = null;
	}
 	
 	public static void main(String[] args)
 	{
 		UDPConnection u;
		try {
			u = UDPConnection.getInstance();
	 		
	 		u.write("Bonjour");
	 		System.out.println(u.read());
	 		
	 		u.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}

 	}
 	
 }
