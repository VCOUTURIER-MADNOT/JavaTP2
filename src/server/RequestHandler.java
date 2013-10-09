package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.MethodInvoker;

public class RequestHandler {

	private ExecutorService threadPool;
	
	public RequestHandler() {
		this.threadPool = Executors.newCachedThreadPool();
	}
		
	public void register(Socket _socket)
	{
		// Obtenir les données brutes - ici receive compris
		// Passer des données brutes à une chaine XML
		// Passer d'une chaine XML à une methode
		// Submit la tache au pool (contenant la methode et ses params)
		// Création du callback et set du socket
		// Ajout du future et le callback dans la map
	}
	
	public void register(DatagramPacket _datagramPacket)
	{
		String xmlString = util.Serialization.ByteArrayToXMLString(_datagramPacket.getData());
		
		final MethodInvoker mp = util.Serialization.XMLToMethod(xmlString);
		final DatagramPacket dp = _datagramPacket;
		
		this.threadPool.submit(new Runnable() {
			
			@Override
			public void run() {
				
				// Recupere le retour de la méthode
				Object obj = mp.invokeMethod();
				
				String xmlString = util.Serialization.ResultToXML(obj);
				
				byte[] byteToSend = util.Serialization.StringToByteArray(xmlString);
				
				try {
					DatagramSocket ds = new DatagramSocket();
					
					DatagramPacket packet = new DatagramPacket(byteToSend, byteToSend.length, dp.getAddress(), dp.getPort());
					
					ds.send(dp);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	
}