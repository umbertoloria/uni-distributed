package esercizi.client_server_rubrica;

import java.io.Serializable;

public class RecordRegistro implements Serializable {

	private static long serialVersionUID = 5654654;

	public final String nome, indirizzo;

	public RecordRegistro(String nome, String indirizzo) {
		this.nome = nome;
		this.indirizzo = indirizzo;
	}

}
