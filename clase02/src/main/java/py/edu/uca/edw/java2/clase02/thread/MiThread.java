package py.edu.uca.edw.java2.clase02.thread;

public class MiThread extends Thread {

	public static void main(String[] args) {
		PruebaThread1 thread = new PruebaThread1("Thread con herencia");		
		RunnableThread runnableThread = new RunnableThread();
		
		
		thread.start();
		
		Thread threadConRunnable = new Thread(runnableThread,  "Thread con Runnable");
		threadConRunnable.start();
		try {
			Thread.sleep(5000);
			thread.interrupt();
//			thread.stop();
//			thread = null;
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
