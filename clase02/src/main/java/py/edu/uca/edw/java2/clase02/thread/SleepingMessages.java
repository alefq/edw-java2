package py.edu.uca.edw.java2.clase02.thread;

public class SleepingMessages extends Thread{
	
	private int contador = 1;
	
	public void run(){

		try {
			Thread t = Thread.currentThread();
			
			while(true){	
				
				if(t.isInterrupted()){
					System.out.println("Interrumpido en estado activo");
					return;
				}
				
				System.out.println("Mensaje "+contador++);
				Thread.sleep(1000);
				if(contador %2 == 0)
				{
					new Exception("Contador par").printStackTrace();
				}
				
			}

		} catch (InterruptedException e) {
			System.out.println("Interrumpido durante el sleep");
		}
		
	}	
}
