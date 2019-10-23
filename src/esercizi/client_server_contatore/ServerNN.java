package esercizi.client_server_contatore;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

// Il server riceve una connessione, apre un thread, riceve un numero, lo somma al contatore, ed invia questo valore.
// Ripete per quanti numeri riceve.
public class ServerNN extends Thread {

	private Socket s;

	public ServerNN(Socket s) {
		this.s = s;
	}

	public void run() {
		try {
			Logger log = Logger.getLogger("Server thread " + Thread.currentThread().getName() + " logs");
			log.info("Socket accepted");

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			log.info("Socket resources initialized");

			while (!s.isClosed()) {

				try {

					int x = Integer.parseInt(ois.readObject().toString());
					log.info("Received a number (" + x + ")");

					synchronized (lock) {
						contatore += x;
					}
					oos.writeObject(contatore);
					oos.flush();
					System.out.println("Contatore: " + contatore);
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
		}
	}

	private static final Object lock = new Object();
	private static int contatore = 0;

	public static void main(String[] args) {
		Logger log = Logger.getLogger("Server logs");
		try {

			ServerSocket ss = new ServerSocket(9137);
			log.info("ServerSocket opened");

			List<Thread> threads = new LinkedList<>();

			while (!ss.isClosed()) {

				Socket s = ss.accept();
				log.info("Socket accepted");

				Thread newThread = new ServerNN(s);
				threads.add(newThread);
				newThread.start();

			}

			ss.close();
			log.info("ServerSocket closed");

			for (Thread thread : threads) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
					log.severe("Unable to wait thread " + thread.getName());
				}
			}
			log.info("Joined all threads");

		} catch (IOException e) {
			e.printStackTrace();
			log.severe("Exception thrown: " + e.getMessage());
		}
	}

}
