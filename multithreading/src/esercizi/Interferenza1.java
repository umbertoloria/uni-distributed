package esercizi;

public class Interferenza1 {

	/*private static class Counter {

		private int i = 0;

		private void increment() {
			i++;
		}

		public String toString() {
			return i + "";
		}

	}*/

	private static class Incrementatore extends Thread {

		// private Counter c;
		private int number;

		// public Incrementatore(Counter c, int number) {
		public Incrementatore(int number) {
			// this.c = c;
			this.number = number;
		}

		public void run() {
			for (int i = 0; i < number; i++) {
				// c.increment();
				synchronized (lock) {
					contatore++;
				}
			}
		}
	}

	private static final int THREADS_COUNT = 3;
	private static final int INCREMENTI = 40_000;
	private static int contatore = 0;
	private static final Object lock = new Object();

	public static void main(String[] args) {
//		Counter c = new Counter();
		Thread[] threads = new Thread[THREADS_COUNT];
		int chunk4thread = INCREMENTI / threads.length;
		for (int i = 0; i < threads.length - 1; i++) {
			threads[i] = new Incrementatore(chunk4thread);
		}
		threads[threads.length - 1] = new Incrementatore(INCREMENTI - (threads.length - 1) * chunk4thread);
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
		System.out.println(contatore);
	}

}
