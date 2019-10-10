package esercizi;

public class Interferenza1 {

	private static class Counter {

		private int i = 0;

		private void increment() {
			i++;
		}

		public String toString() {
			return i + "";
		}

	}

	private static class Incrementatore extends Thread {

		private Counter c;
		private int number;

		public Incrementatore(Counter c, int number) {
			this.c = c;
			this.number = number;
		}

		public void run() {
			for (int i = 0; i < number; i++) {
				c.increment();
			}
		}
	}

	public static void main(String[] args) {
		Counter c = new Counter();
		Thread[] threads = new Thread[1];
		int chunk4thread = 40_000 / threads.length;
		for (int i = 0; i < threads.length - 1; i++) {
			threads[i] = new Incrementatore(c, chunk4thread);
		}
		threads[threads.length - 1] = new Incrementatore(c, 40_000 - (threads.length - 1) * chunk4thread);
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
		System.out.println(c);
	}

}
