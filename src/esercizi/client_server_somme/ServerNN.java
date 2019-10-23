package esercizi.client_server_somme;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

// Il server riceve due numeri ed invia la loro somma. Ripete per ogni coppia di numeri. Questo, per più client.
public class ServerNN extends Thread {

	private Socket s;

	public ServerNN(Socket s) {
		this.s = s;
		setName("Server thread for port " + s.getLocalPort());
	}

	public void run() {
		Logger log = Logger.getLogger("Server thread " + Thread.currentThread().getName() + " logs");
		try {

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			log.info("Socket resources initialized");

			while (!s.isClosed()) {

				try {

					int x = Integer.parseInt(ois.readObject().toString());
					int y = Integer.parseInt(ois.readObject().toString());

					int result = x + y;
					oos.writeObject(result);
					oos.flush();

					System.out.println("Client served " + x + " + " + y + " = " + result);

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

	public static void main(String[] args) {
		Logger log = Logger.getLogger("Server logs");
		try {

			ServerSocket ss = new ServerSocket(9592);
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
