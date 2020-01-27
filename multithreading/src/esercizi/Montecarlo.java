package esercizi;

import java.util.Random;

public class Montecarlo {

	private static class PiGreco extends Thread {

		private long c, n;

		public PiGreco(long n) {
			this.n = n;
		}

		public void run() {
			Random r = new Random();
			c = 0;
			for (int i = 0; i < n; i++) {
				float x = r.nextFloat();
				float y = r.nextFloat();
				if (x * x + y * y < 1) {
					c++;
				}
			}
		}

		public long getC() {
			return c;
		}

		public long getN() {
			return n;
		}

	}

	public static void main(String[] args) {
		long times = 100_000_000L;
		PiGreco[] threads = new PiGreco[4];
		long times4thread = times / threads.length;
		for (int i = 0; i < threads.length - 1; i++) {
			threads[i] = new PiGreco(times4thread);
		}
		threads[threads.length - 1] = new PiGreco(times - times4thread * (threads.length - 1));
		long inizio = System.currentTimeMillis();
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long fine = System.currentTimeMillis();
		long c = 0;
		for (PiGreco thread : threads) {
			c += thread.getC();
		}
		System.out.printf("risultato: %15.14f\n", 4.0f * c / times);
		System.out.println("durata:    " + (fine - inizio) / 1000f + " s");
	}

}