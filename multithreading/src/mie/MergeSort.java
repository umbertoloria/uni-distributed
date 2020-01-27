package mie;

public class MergeSort extends Thread {

	private static Object lock = new Object();

	private static void print(int[] dest, int begin, int end) {
		StringBuilder str = new StringBuilder();
		synchronized (lock) {
			synchronized (dest) {
				str.append('|');
				for (int i = 0; i < dest.length; i++) {
					if (begin <= i && i < end) {
						str.append(dest[i]);
					} else {
						str.append(' ');
					}
					str.append('|');
				}
			}
		}
		System.out.println(str);
	}

	private static int nThreads = 0;

	public static void main(String[] args) {
		// TODO: Numero di thread fissato.
		int[] arr = {5, 1, 7, 3, 6, 4, 2, 8};
		print(arr, 0, arr.length);
		MergeSort ms = new MergeSort(arr, 0, arr.length);
		ms.start();
		nThreads++;
		try {
			ms.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		print(arr, 0, arr.length);
		System.out.println("Numero threads: " + nThreads);
	}

	private int[] source;
	private int begin;
	private int end;
	private int[] app;

	public MergeSort(int[] source, int begin, int end) {
		this.source = source;
		this.begin = begin;
		this.end = end;
		this.app = new int[source.length];
	}

	public void run() {
//		System.out.println("start");
//		print(source, begin, end);
		if (begin + 2 < end) {
			int mid = (begin + end) / 2;
//			System.out.println(begin + "/" + end + " -> " + mid);
			Thread t1 = new MergeSort(source, begin, mid);
			Thread t2 = new MergeSort(source, mid, end);
			t1.start();
			nThreads++;
			t2.start();
			nThreads++;
			try {
				t1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			print(source, begin, end);
			merge(source, begin, mid, end);
		} else if (begin + 2 == end) {
			if (source[begin] > source[end - 1]) {
				int tmp = source[begin];
				source[begin] = source[end - 1];
				source[end - 1] = tmp;
			}
		}
//		print(source, begin, end);
	}

	private void merge(int[] source, int begin, int mid, int end) {
//		System.out.println("start partition");
//		print(source, begin, end);
		int[] app = new int[source.length];
		int i = begin;
		int j = mid;
		int k = begin;
		while (i < mid && j < end) {
			int comp = source[i] - source[j];
			if (comp < 0) {
				app[k++] = source[i];
				i++;
			} else if (comp == 0) {
				app[k++] = source[j];
				app[k++] = source[i];
				i++;
				j++;
			} else {
				app[k++] = source[j];
				j++;
			}
		}
		while (i < mid) {
			app[k++] = source[i];
			i++;
		}
		while (j < end) {
			app[k++] = source[j];
			j++;
		}
		i = begin;
		while (i < end) {
			source[i] = app[i];
			i++;
		}
//		print(source, begin, end);
//		System.out.println("end partition");
	}

}
