package esercizi.client_server_saluto;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {

	private static Logger logger = Logger.getLogger("global");

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(9000);
			logger.info("Socket istanziato, accettato connessioni...");
			Socket socket = serverSocket.accept();
			logger.info("Accettata una connessione... attendo comandi.");
			ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
			String nome = (String) inStream.readObject();
			logger.info("Ricevuto: " + nome);
			outStream.writeObject("Hello " + nome);
			socket.close();
		} catch (EOFException e) {
			logger.severe("Problemi con la connessione: " + e.getMessage());
			e.printStackTrace();
		} catch (Throwable e) {
			logger.severe("Lanciata throwable: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
