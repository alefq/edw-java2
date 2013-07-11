package py.edu.uca.edw.java2.clase02.thread;

public class PruebaThread1 extends Thread {

	private int contador = 5;

	public PruebaThread1(String nombre) {
		super(nombre);
	}

	public void run() {

		while (true) {
			System.out.println("Thread " + getName() + " - Valor Contador: "
					+ contador--);
			try {
				int durmiendo = (int) (Math.random() * 10000);
				System.out.println("     " +  getName()  + " durmiendo: " + (durmiendo/1000) + " segundos...");
				sleep(durmiendo);
			} catch (InterruptedException e) {
				e.printStackTrace(System.err);
			}

		}

	}
}
