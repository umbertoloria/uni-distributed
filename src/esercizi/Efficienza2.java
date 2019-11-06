package esercizi;

public class Efficienza2 {

	private static class Sommatore extends Thread {

		private int[] array;
		private int begin, end;
		private long somma;

		public Sommatore(int[] array, int begin, int end) {
			this.array = array;
			this.begin = begin;
			this.end = end;
		}

		public void run() {
			long somma = 0;
			for (int i = begin; i < end; i++) {
				array[i] = 1;
				somma += array[i];
			}
			this.somma = somma;
		}

	}

	private static class Massimatore extends Thread {

		private int[] array;
		private int begin, end;
		private int max;

		public Massimatore(int[] array, int begin, int end) {
			this.array = array;
			this.begin = begin;
			this.end = end;
		}

		public void run() {
			int max = array[begin];
			for (int i = begin + 1; i < end; i++) {
				array[i] = 1;
				if (max < array[i]) {
					max = array[i];
				}
			}
			this.max = max;
		}

	}

	private static final int ARRAY_SIZE = 1_000_200_000;

	private enum Scelta {
		SOMMATORE, MASSIMATORE
	}

	private static long init(int nThreads, Scelta scelta) {
		int[] array = new int[ARRAY_SIZE];
		Thread[] threads = new Thread[nThreads];
		int chunk4thread = array.length / threads.length;
		int begin = 0;
		int end = chunk4thread;
		for (int i = 0; i < threads.length - 1; i++) {
			if (scelta == Scelta.SOMMATORE) {
				threads[i] = new Sommatore(array, begin, end);
			} else if (scelta == Scelta.MASSIMATORE) {
				threads[i] = new Massimatore(array, begin, end);
			}
			begin += chunk4thread;
			end += chunk4thread;
		}
		threads[threads.length - 1] = new Sommatore(array, begin, array.length);
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
		return System.currentTimeMillis() - inizio;
	}

	public static void main(String[] args) {
		System.out.println("SOMMA");
		System.out.println("1 thread:  " + init(1, Scelta.SOMMATORE) + " ms");
		for (int i = 2; i <= 8; i += 2) {
			System.out.println(i + " threads: " + init(i, Scelta.SOMMATORE) + " ms");
		}
		System.out.println("MASSIMO");
		System.out.println("1 thread:  " + init(1, Scelta.MASSIMATORE) + " ms");
		for (int i = 2; i <= 8; i += 2) {
			System.out.println(i + " threads: " + init(i, Scelta.MASSIMATORE) + " ms");
		}
	}

}
