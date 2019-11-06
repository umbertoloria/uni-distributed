package esercizi.client_server_rubrica;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RegistroServer {

	private static Logger logger = Logger.getLogger("global");

	public static void main(String[] args) {
		Map<String, RecordRegistro> registro = new HashMap<>();
		Socket socket = null;
		System.out.println("In attesa di connessioni...");
		try {
			ServerSocket serverSocket = new ServerSocket(7000);
			while (true) {
				socket = serverSocket.accept();
				ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
				RecordRegistro record = (RecordRegistro) inStream.readObject();
				if (record.indirizzo != null) {
					logger.info("Inserisco: " + record.nome + ", " + record.indirizzo);
					registro.put(record.nome, record);
				} else {
					logger.info("Cerco: " + record.nome);
					RecordRegistro res = registro.get(record.nome);
					ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
					outStream.writeObject(res);
					outStream.flush();
				}
				socket.close();
			}
		} catch (EOFException e) {
			logger.severe("Problemi con la connessione: " + e.getMessage());
			e.printStackTrace();
		} catch (Throwable e) {
			logger.severe("Lancia Throwable: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

}
