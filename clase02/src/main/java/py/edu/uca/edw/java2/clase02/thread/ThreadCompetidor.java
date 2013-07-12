package py.edu.uca.edw.java2.clase02.thread;

import java.util.Date;

import org.apache.log4j.Logger;

public class ThreadCompetidor extends Thread {

	Logger log = Logger.getLogger(ThreadCompetidor.class);
	
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
			log.info("¡Thread " + getName() + " llegó a la meta!");
			log.info("¡Thread " + getName() + " llegada: " + ahora);
		}
	}

	private int revisarDificultades(int distancia) {
		while (distancia > 0) {
			log.info("Thread " + getName()
					+ " - distancia a la meta: " + distancia--);
			try {
				double random = Math.random();
				log.debug("Random: " + random);
				
				long tiempoEnSegundos = (long) (random * 10);
				
				long tiempoEnMilisegundos = tiempoEnSegundos * 1000;
				
				long tiempoParaAvanzar1Km = tiempoEnMilisegundos;
				
				log.info("     " + getName() + " recorriendo 1Km en "
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
