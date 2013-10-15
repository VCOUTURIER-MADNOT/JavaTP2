package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public final class TCPConnection {

	
    private static volatile TCPConnection instance = null;
 
    
	private String host = "0.0.0.0";
	private int port = 51512;
	
	private Socket s = null;
	private BufferedWriter writer = null;
	private BufferedReader reader = null;	
 
	
    private TCPConnection() throws IOException {
        s = new Socket(host, port);
		writer =  new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));	
		
    }
 
    public final static TCPConnection getInstance() throws IOException {
        if (TCPConnection.instance == null) {
           synchronized(TCPConnection.class) {
             if (TCPConnection.instance == null) {
            	 TCPConnection.instance = new TCPConnection();
             }
           }
        }
        return TCPConnection.instance;
    }

	public String readLine() throws IOException{
		String ligne;
		if ((ligne = reader.readLine()) != null){
			return ligne;
		}
		return "";
	}

	public void write(String str) throws IOException{
		if (str != null){
			writer.write(str);
			writer.flush();
		}
	}
	
 	public void destroy() throws IOException{
		s.close();
		writer.close();
		reader.close();
		TCPConnection.instance = null;
	}
 	 	
 	public static void main(String[] args){
 		try {
 			TCPConnection sc = TCPConnection.getInstance();
 			sc.write("<action connectionString=\"user001:ff5f0a30f031f9674d92933531df0180\" method=\"testMethod\"><java.lang.Integer>1</java.lang.Integer><java.lang.String>Test</java.lang.String></action>");
 			System.out.println("Recu " + sc.readLine());
 			sc.destroy();
 		}
 		catch (IOException io){
 			System.out.println(io.getMessage());
 		}
 	}
 	
 }


