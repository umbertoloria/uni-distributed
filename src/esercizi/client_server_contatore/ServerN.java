package esercizi.client_server_contatore;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

// Il server riceve un numero, lo somma al contatore, ed invia questo valore. Ripete per quanti numeri riceve.
public class ServerN {

	public static void main(String[] args) {
		Logger log = Logger.getLogger("Server logs");
		try {

			ServerSocket ss = new ServerSocket(9137);
			log.info("ServerSocket opened");

			Socket s = ss.accept();
			log.info("Socket accepted");

			ss.close();
			log.info("ServerSocket closed");

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			log.info("Socket resources initialized");

			int contatore = 0;

			while (!s.isClosed()) {

				try {

					int x = Integer.parseInt(ois.readObject().toString());
					log.info("Received a number (" + x + ")");

					contatore += x;
					oos.writeObject(contatore);
					oos.flush();
					log.info("Result (" + contatore + ") sent");

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
