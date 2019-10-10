package launcher;

public class Sommatore implements Runnable {

	private int n;
	private float[] numeri;
	private int offset, count;

	public Sommatore(int n, float[] numeri, int offset, int count) {
		this.n = n;
		this.numeri = numeri;
		this.offset = offset;
		this.count = count;
		System.out.printf("Core %8d;   offset %14d;   count %13d\n", n, offset, count);
	}

	public void run() {
		long inizio = System.currentTimeMillis();
		float somma = 0;
		for (int i = offset; i < offset + count; i++) {
			somma += numeri[i];
		}
		long fine = System.currentTimeMillis();
		somma /= count;
		System.out.printf("Calcolatore %d:   media %15.3f;   durata %11.3fs\n", n, somma, (fine - inizio) / 1000f);
	}

}
