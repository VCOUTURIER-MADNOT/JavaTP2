package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.MethodInvoker;

public class RequestHandler {

	private ExecutorService threadPool;
	
	public RequestHandler() {
		this.threadPool = Executors.newCachedThreadPool();
	}
		
	
	public void register(Socket _s)
	{

		InputStream is;

		byte[] byteToReceive = new byte[1024];
		
		try {
			is = _s.getInputStream();
			is.read(byteToReceive);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String xmlString = util.Serialization.ByteArrayToXMLString(byteToReceive);
		
		final MethodInvoker mp = util.Serialization.XMLToMethod(xmlString);
		final Socket s = _s;
		
		String connectionString = util.Serialization.getConnectionString(xmlString);
		
		Map<String, Map<String, String>> m = API.getMethods(true, connectionString);
		
		String methodName = mp.getMethod().getName();
		
		if (m != null && m.containsKey(methodName))
		{
			this.threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					
					// Recupere le retour de la méthode
					
					Object obj = mp.invokeMethod();
					
					String xmlString = util.Serialization.ResultToXML(obj);
					
					byte[] byteToSend = util.Serialization.StringToByteArray(xmlString);
					
					try {
						
						OutputStream os = s.getOutputStream();
						os.write(byteToSend);
						os.flush();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			});
		}
			
		else 
		{
			this.threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					
					String xmlString = "<error>Incorrect credentials or insufficient user access</error>";
					
					byte[] byteToSend = util.Serialization.StringToByteArray(xmlString);
					
					try {
						OutputStream os = s.getOutputStream();
						os.write(byteToSend);
						os.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			});
		}
	}
	
	public void register(DatagramPacket _datagramPacket)
	{
		String xmlString = util.Serialization.ByteArrayToXMLString(_datagramPacket.getData());
		
		final MethodInvoker mp = util.Serialization.XMLToMethod(xmlString);
		final DatagramPacket dp = _datagramPacket;
		
		String connectionString = util.Serialization.getConnectionString(xmlString);
		
		Map<String, Map<String, String>> m = API.getMethods(true, connectionString);
		
		String methodName = mp.getMethod().getName();
		
		if (m != null && m.containsKey(methodName))
		{
			this.threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					
					// Recupere le retour de la méthode
					
					Object obj = mp.invokeMethod();
					
					String xmlString = util.Serialization.ResultToXML(obj);
					
					byte[] byteToSend = util.Serialization.StringToByteArray(xmlString);
					
					try {
						DatagramSocket ds = new DatagramSocket();
						
						DatagramPacket packet = new DatagramPacket(byteToSend, byteToSend.length, dp.getAddress(), dp.getPort());
						
						ds.send(packet);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			});
		}
			
		else 
		{
			this.threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					
					String xmlString = "<error>Incorrect credentials or insufficient user access</error>";
					
					byte[] byteToSend = util.Serialization.StringToByteArray(xmlString);
					
					
					try {
						DatagramSocket ds = new DatagramSocket();
						
						DatagramPacket packet = new DatagramPacket(byteToSend, byteToSend.length, dp.getAddress(), dp.getPort());
						
						ds.send(packet);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			});
		}
		
		
	}
	
}