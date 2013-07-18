package py.edu.uca.edw.java2.clase02.thread;

import java.util.Date;

import py.edu.uca.edw.java2.clase02.log.LogUtil;

public class ThreadRace {

	public static final int KM_CARRERA = 5;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Inicializamos nuestro framework de logging */
		LogUtil.basicLog4jConfiguration();
		/* Construimos nuestros dos competidores */
		ThreadCompetidor competidor1 = new ThreadCompetidor();
		ThreadCompetidor competidor2 = new ThreadCompetidor(
				ThreadRace.KM_CARRERA);

		/* La fecha/hora de inicio del a carrera */
		Date ahora = new Date();
		System.out.println("Inicio de la Carrera: " + ahora);

		/* Bautizamos a los corredores */
		competidor1.setName("Ayrton Senna");
		competidor2.setName("Michael Schumaher");

		/*
		 * Iniciamos ambos threads que simulan a los corredores recorriendo la
		 * misma distancia, pero con distintas dificultades
		 */
		competidor1.start();
		competidor2.start();
	}

}
