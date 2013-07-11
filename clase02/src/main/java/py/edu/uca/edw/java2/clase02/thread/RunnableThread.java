package py.edu.uca.edw.java2.clase02.thread;

public class RunnableThread implements Runnable {

	private int contador = 5;

	public void run() {

		while (contador > 0) {
			System.out.println("Thread " + Thread.currentThread().getName()
					+ " - Valor contador " + contador--);
			try {
				int durmiendo = (int) (Math.random() * 10000);
				System.out.println("     " + Thread.currentThread().getName() + " durmiendo: "
						+ (durmiendo / 1000) + " segundos...");
				Thread.sleep(durmiendo);
			} catch (InterruptedException e) {
				e.printStackTrace(System.err);
			}
		}
	}

}
