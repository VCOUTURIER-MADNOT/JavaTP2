package server;

import java.net.DatagramPacket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RequestHandler extends Thread {

	private ExecutorService threadPool;
	private Map<Future, Callable> mapFuture;
	
	
	private class CallbackUDP implements Callable
	{
		private Object obj;
		private DatagramPacket dp;
		
		@Override
		public Object call() throws Exception {
			// Appel du ResultToXML
			// Renvoie au client du XML en sortie
			return null;
		}
	}
	
	private class CallbackTCP implements Callable
	{
		private Object obj;
		private Socket s;
		
		@Override
		public Object call() throws Exception {
			// Appel du ResultToXML
			// Renvoie au client du XML en sortie
			return null;
		}
	}
	
	public RequestHandler() {
		this.threadPool = Executors.newCachedThreadPool();
		this.mapFuture = new HashMap<Future, Callable>();
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
		// Obtenir les données brutes depuis le DatagramPacket
		// Passer des données brutes à une chaine XML
		// Passer d'une chaine XML à une methode
		// Submit la tache au pool (contenant la methode et ses params)
		// Création du callback et set du DatagramPacket
		// Ajout du future et le callback dans la map
	}
	
	
	public void run()
	{
		// TODO : Gestion du join
		while(true)
		{
			// Checker s'il y a un Future de terminé
				// Si oui, get le future et le setter dans le callable (CallbackUDP ou CallbackTCP)
						// lancer le callable
		}
	}
}