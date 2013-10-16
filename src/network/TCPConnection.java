package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class TCPConnection implements Connection {
 
    
	private String host = "0.0.0.0";
	private int port = 51510;
	
	private Socket s = null;
	private BufferedWriter writer = null;
	private BufferedReader reader = null;	
 
	
    public TCPConnection() throws IOException {
        s = new Socket(host, port);
		writer =  new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));	
		
    }
 
	public String readData() throws IOException{

		char[] byteReceived = new char[1024];
		reader.read(byteReceived, 0, 1024);
	 	String s = new String(byteReceived).trim();
	 	
	 	if(s.startsWith("<error>"))
	 		throw new IOException("Erreur");
	 	
	 	return s;
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
	}
 	
 }


