package py.edu.uca.edw.java2.clase02.thread;

import py.edu.uca.edw.java2.clase01.net.QuienSoy;

public class RunnableThread extends QuienSoy implements Runnable {

	private int contador = 5;

	public void run() {

		while (contador > 0) {
			/*
			 * Esta clase que implementar Runnable, correrá este ciclon
			 * "contador" veces,
			 */
			System.out.println("Thread " + Thread.currentThread().getName()
					+ " - Valor contador " + contador--);
			try {
				/*
				 * 5 veces, en este ejemplo, se calcula un nro. aleatorio para
				 * dormir al thread
				 */
				int durmiendo = (int) (Math.random() * 10000);
				System.out.println("     " + Thread.currentThread().getName()
						+ " durmiendo: " + (durmiendo / 1000) + " segundos...");
				Thread.sleep(durmiendo);
			} catch (InterruptedException e) {
				e.printStackTrace(System.err);
			}
		}
	}

}
