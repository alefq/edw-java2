package py.edu.uca.edw.java2.clase02.thread;

import java.util.Date;

public class ThreadRace {

	public static final int KM_CARRERA=5;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadCompetidor competidor1 = new ThreadCompetidor();
		ThreadCompetidor competidor2 = new ThreadCompetidor(ThreadRace.KM_CARRERA);

		
		Date ahora = new Date();
		System.out.println("Inicio de la Carrera: " + ahora);

		
		competidor1.setName("Competidor #1");
		competidor2.setName("Competidor #2");

		
		competidor1.start();
		competidor2.start();
	}

}
