package esercizi.client_server_contatore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

// Il client invia un numero e riceve un risutato, che stampa a video.
public class Client {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Logger log = Logger.getLogger("Client logs");
		try {

			Socket s = new Socket("localhost", 9137);
			log.info("Socket enstablished");

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			log.info("Socket resources initialized");


			System.out.println("Fornisci il prossimo numero da inviare (0 per chiudere): ");
			int x = in.nextInt();
			while (x > 0) {

				oos.writeObject(x);
				oos.flush();
				log.info("Parameter (" + x + ") sent");

				int result = Integer.parseInt(ois.readObject().toString());
				System.out.println("result: " + result);
				log.info("Result taken");

				System.out.println("Fornisci il prossimo numero da inviare (0 per chiudere): ");
				x = in.nextInt();

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
