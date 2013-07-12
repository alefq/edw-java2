package py.edu.uca.edw.java2.clase02.thread;

import java.util.Date;

public class ThreadCompetidor extends Thread {

	private int distancia;

	public ThreadCompetidor() {
		setDistancia(ThreadRace.KM_CARRERA);
	}

	public ThreadCompetidor(int distancia) {
		setDistancia(distancia);
	}

	@Override
	public void run() {
		//int distancia = ThreadRace.KM_CARRERA;
		distancia = revisarDificultades(distancia);
		if (distancia <= 0) {
			Date ahora = new Date();
			System.out.println("¡Thread " + getName() + " llegó a la meta!");
			System.out.println("¡Thread " + getName() + " llegada: " + ahora);
		}
	}

	private int revisarDificultades(int distancia) {
		while (distancia > 0) {
			System.out.println("Thread " + getName()
					+ " - distancia a la meta: " + distancia--);
			try {
				double random = Math.random();
				System.out.println("Random: " + random);
				
				long tiempoEnSegundos = (long) (random * 10);
				
				long tiempoEnMilisegundos = tiempoEnSegundos * 1000;
				
				long tiempoParaAvanzar1Km = tiempoEnMilisegundos;
				
				System.out.println("     " + getName() + " recorriendo 1Km en "
						+ (tiempoParaAvanzar1Km / 1000) + " segundos...");
				sleep(tiempoParaAvanzar1Km);
			} catch (InterruptedException e) {
				e.printStackTrace(System.err);
			}
		}
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public int getDistancia() {
		return distancia;
	}
}
