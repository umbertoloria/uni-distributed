package esercizi;

public class Efficienza1 {

	private static class Assegnatore extends Thread {

		private int[] array;
		private int begin, end;

		public Assegnatore(int[] array, int begin, int end) {
			this.array = array;
			this.begin = begin;
			this.end = end;
		}

		public void run() {
			for (int i = begin; i < end; i++) {
				array[i] = 42;
			}
		}

	}

	private static final int ARRAY_SIZE = 1_000_200_000;

	private static long init1() {
		int[] array = new int[ARRAY_SIZE];
		long inizio = System.currentTimeMillis();
		for (int i = 0; i < array.length; i++) {
			array[i] = 42;
		}
		return System.currentTimeMillis() - inizio;
	}

	private static long init2() {
		int[] array = new int[ARRAY_SIZE];
		Thread a, b;
		a = new Assegnatore(array, 0, ARRAY_SIZE / 2);
		b = new Assegnatore(array, ARRAY_SIZE / 2, ARRAY_SIZE);
		long inizio = System.currentTimeMillis();
		a.start();
		b.start();
		try {
			a.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			b.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis() - inizio;
	}

	private static long init3() {
		int[] array = new int[ARRAY_SIZE];
		Thread a, b, c, d;
		a = new Assegnatore(array, 0, ARRAY_SIZE / 4);
		b = new Assegnatore(array, ARRAY_SIZE / 4, ARRAY_SIZE / 2);
		c = new Assegnatore(array, ARRAY_SIZE / 2, ARRAY_SIZE / 4 * 3);
		d = new Assegnatore(array, ARRAY_SIZE / 4 * 3, ARRAY_SIZE);
		long inizio = System.currentTimeMillis();
		a.start();
		b.start();
		c.start();
		d.start();
		try {
			a.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			b.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			c.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			d.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis() - inizio;
	}

	public static void main(String[] args) {
		System.out.println("1 thread:  " + init1() + " ms");
		System.out.println("2 threads: " + init2() + " ms");
		System.out.println("4 threads: " + init3() + " ms");
	}

}
