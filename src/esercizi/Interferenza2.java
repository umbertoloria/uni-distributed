package esercizi;

public class Interferenza2 {

	private static class Smiley extends Thread {

		public void run() {
			while (true) {
				try {
					synchronized (Smiley.class) {
						printDuePunti();
						printTrattino();
						printParentesiChiusa();
					}
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void printParentesiChiusa() throws InterruptedException {
			System.out.println(")");
			Thread.sleep(100);
		}

		private void printTrattino() throws InterruptedException {
			System.out.print("-");
			Thread.sleep(100);
		}

		private void printDuePunti() throws InterruptedException {
			System.out.print(":");
			Thread.sleep(100);
		}

	}

	public static void main(String[] args) {
		new Smiley().start();
		new Smiley().start();
	}

}