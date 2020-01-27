package esercizi.client_server_rubrica;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ShellClient {

	private static Logger logger = Logger.getLogger("global");
	private static Scanner in;

	public static void main(String[] args) {
		String host = "localhost";
		String cmd;
		in = new Scanner(System.in);
		try {
			while (!(cmd = ask("Comandi>")).equals("quit")) {
				if (cmd.equals("inserisci")) {
					System.out.println("Inserisce i dati.");
					String nome = ask("Nome: ");
					String indirizzo = ask("Indirizzo: ");
					RecordRegistro r = new RecordRegistro(nome, indirizzo);
					Socket socket = new Socket(host, 7000);
					ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
					outStream.writeObject(r);
					outStream.flush();
					socket.close();
				} else if (cmd.equals("cerca")) {
					System.out.println("Inserire il nome per la ricerca.");
					String nome = ask("Nome: ");
					RecordRegistro r = new RecordRegistro(nome, null);
					Socket socket = new Socket(host, 7000);
					ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
					outStream.writeObject(r);
					outStream.flush();
					ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
					RecordRegistro result = (RecordRegistro) inStream.readObject();
					if (result != null) {
						System.out.println("Indirizzo: " + result.indirizzo);
					} else {
						System.out.println("Record non presente");
					}
					socket.close();
				} else {
					System.out.println("Cosa?");
				}
			}
		} catch (Throwable e) {
			logger.severe("Lanciata Throwable: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Bye bye");
	}

	private static String ask(String prompt) {
		System.out.print(prompt + " ");
		return in.nextLine();
	}

}
