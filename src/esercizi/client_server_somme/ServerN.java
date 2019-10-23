package esercizi.client_server_somme;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

// Il server riceve due numeri ed invia la loro somma. Ripete questo per ogni coppia che gli viene inviata.
public class ServerN {

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

			while (!s.isClosed()) {

				try {

					int x = Integer.parseInt(ois.readObject().toString());
					int y = Integer.parseInt(ois.readObject().toString());
					log.info("Received two numbers (" + x + ", " + y + ")");

					int result = x + y;
					oos.writeObject(result);
					oos.flush();
					log.info("Result (" + result + ") sent");

				} catch (EOFException | ClassNotFoundException e) {
					break;
				}

			}

			oos.close();
			ois.close();
			s.close();
			log.info("Socket resources closed");

		} catch (IOException e) {
			e.printStackTrace();
			log.severe("Exception thrown: " + e.getMessage());
		}
	}

}
