package launcher;

public class Launcher {

	public static void main(String[] args) {

		int card = 1_000_500_000;

		float[] numeri = new float[card];
		for (int i = 0; i < card; i++) {
			numeri[i] = i + 1;
		}

		System.out.println("ORGANIZZAZIONE");

		int core = 5;
		int chunk4core = numeri.length / core;
		Sommatore[] cores = new Sommatore[core];
		int offset = 0;
		for (int i = 0; i < cores.length - 1; i++) {
			cores[i] = new Sommatore(i + 1, numeri, offset, chunk4core);
			offset += chunk4core;
		}
		cores[cores.length - 1] = new Sommatore(cores.length, numeri, offset, numeri.length - (cores.length - 1) * chunk4core);

		System.out.println("\nCALCOLO PARALLELO");
		long inizio = System.currentTimeMillis();
		Thread[] threads = new Thread[cores.length];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(cores[i]);
		}
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException ignored) {
			}
		}
		long fine = System.currentTimeMillis();
		System.out.printf("Durata parallela %10.3fs\n", (fine - inizio) / 1000f);

		System.out.println("\nCALCOLO SEQUENZIALE");
		inizio = System.currentTimeMillis();
		for (Sommatore sommatore : cores) {
			sommatore.run();
		}
		fine = System.currentTimeMillis();
		System.out.printf("Durata sequenziale %10.3fs\n", (fine - inizio) / 1000f);

	}

}