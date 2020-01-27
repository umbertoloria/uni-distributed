package esercizi.client_server_somme;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

// Il server riceve due numero ed invia la loro somma.
public class Server1 {

	public static void main(String[] args) {
		Logger log = Logger.getLogger("Server logs");
		try {

			ServerSocket ss = new ServerSocket(9592);
			log.info("ServerSocket opened");

			Socket s = ss.accept();
			log.info("Socket accepted");

			ss.close();
			log.info("ServerSocket closed");

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			log.info("Socket resources initialized");

			int x = Integer.parseInt(ois.readObject().toString());
			int y = Integer.parseInt(ois.readObject().toString());
			log.info("Received two numbers (" + x + ", " + y + ")");

			int result = x + y;
			oos.writeObject(result);
			log.info("Result (" + result + ") sent");

			oos.close();
			ois.close();
			s.close();
			log.info("Socket resources closed");

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			log.severe("Exception thrown: " + e.getMessage());
		}
	}

}
