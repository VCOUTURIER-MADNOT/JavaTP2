package network;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPConnection implements Connection {


    private static volatile UDPConnection instance = null;
 
	private int port = 51511;


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


	public String readData() throws IOException{
		byte[] bytesFromSocket = new byte[1024];
		String msg;
		
		DatagramPacket packet = new DatagramPacket(bytesFromSocket, bytesFromSocket.length);

		this.s.receive(packet);
		msg = new String(bytesFromSocket, 0, bytesFromSocket.length);
		
		return msg.trim();
	}
	
	public void write(String str) throws IOException {
		if (str != null || str != ""){
		    DatagramPacket packet = new DatagramPacket(str.getBytes("UTF-8"), str.getBytes("UTF-8").length, InetAddress.getLocalHost(), this.port);
		    this.s.send(packet);
		}
	}

 	public void destroy(){
		this.s.close();
		UDPConnection.instance = null;
	}
 	
 	public static void main(String[] args)
 	{
 		UDPConnection sc;
		try {
			sc = UDPConnection.getInstance();
	 		sc.write("<action connectionString=\"user001:ff5f0a30f031f9674d92933531df0180\" method=\"testMethod\"><java.lang.Integer>1</java.lang.Integer><java.lang.String>Test</java.lang.String></action>");
	 		System.out.println(sc.readData());
	 		sc.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}

 	}
 	
 }
