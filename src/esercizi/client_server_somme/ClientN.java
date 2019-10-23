package esercizi.client_server_somme;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

// Il client invia una coppia di numeri, riceve la loro somma mandandola in output, e ripete questo per ogni coppia che ha.
public class ClientN {

	public static void main(String[] args) {

		int[] numeri = {
				1, 2,
				5, 3,
				7, 5,
		};
		assert numeri.length % 2 == 0;

		Logger log = Logger.getLogger("Client logs");
		try {

			Socket s = new Socket("localhost", 9592);
			log.info("Socket enstablished");

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			log.info("Socket resources initialized");

			int i = 0;
			while (i < numeri.length) {

				int x = numeri[i++];
				int y = numeri[i++];
				oos.writeObject(x);
				oos.writeObject(y);
				oos.flush();
				log.info("Parameters (" + x + ", " + y + ") sent");

				int result = Integer.parseInt(ois.readObject().toString());
				System.out.println(x + " + " + y + " = " + result);
				log.info("Result taken");

			}

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
