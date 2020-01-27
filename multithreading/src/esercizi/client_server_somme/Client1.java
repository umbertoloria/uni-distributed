package esercizi.client_server_somme;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

// Il client invia due numeri e riceve la loro somma, mandandola in output.
public class Client1 {

	public static void main(String[] args) {
		Logger log = Logger.getLogger("Client logs");
		int x = 5;
		int y = 7;
		try {

			Socket s = new Socket("localhost", 9592);
			log.info("Socket enstablished");

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			log.info("Socket resources initialized");

			oos.writeObject(x);
			oos.writeObject(y);
			oos.flush();
			log.info("Parameters (" + x + ", " + y + ") sent");

			int result = Integer.parseInt(ois.readObject().toString());
			System.out.println(x + " + " + y + " = " + result);
			log.info("Result taken");

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
