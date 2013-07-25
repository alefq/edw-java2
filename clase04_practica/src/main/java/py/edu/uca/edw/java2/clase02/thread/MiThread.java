package py.edu.uca.edw.java2.clase02.thread;

public class MiThread extends Thread {

	public static void main(String[] args) {
		/* Creamos un thread que hereda de la clase java.lang.Thread */
		PruebaThread1 thread = new PruebaThread1("Thread con herencia");
		/*
		 * Creamos una clase que implementa Runnable, lo que significa que puede
		 * correr dentro de un thread
		 */
		RunnableThread runnableThread = new RunnableThread();
		/* Iniciamos la ejecución del thread */
		thread.start();

		/*
		 * Creamos un nuevo thread con la clase que implementa Runnable, que
		 * tiene el comportamiento necesario para correr en un thread
		 */
		Thread threadConRunnable = new Thread(runnableThread,
				"Thread con Runnable");
		/* Iniciamos el thread que contiene a "runnableThead" */
		threadConRunnable.start();
		try {
			/*
			 * Esta es la forma de dormir cualquier programa en java. En este
			 * caso dormimos por 5 segundos y luego interrumpimos al primer
			 * thread creado.
			 */
			Thread.sleep(5000);
			thread.interrupt();
			/*
			 * Thread.stop() está deprecado, cada thread debe tener programado
			 * su forma de detener su ejecución, aquí la explicación completa:
			 * http://docs.oracle.com/javase/1.5.0/docs/guide/misc/
			 * threadPrimitiveDeprecation.html
			 */
			// thread.stop();
			// thread = null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}
}
