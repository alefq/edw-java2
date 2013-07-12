package py.edu.uca.edw.java2.clase02.thread;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class ThreadRace {

	public static final int KM_CARRERA = 5;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		basicLog4jConfiguration();
		ThreadCompetidor competidor1 = new ThreadCompetidor();
		ThreadCompetidor competidor2 = new ThreadCompetidor(
				ThreadRace.KM_CARRERA);

		Date ahora = new Date();
		System.out.println("Inicio de la Carrera: " + ahora);

		competidor1.setName("Competidor #1");
		competidor2.setName("Competidor #2");

		competidor1.start();
		competidor2.start();
	}

	public static void basicLog4jConfiguration() {
		Properties conf = new Properties();
		// conf.put("log4j.rootLogger", "info, myAppender");
		conf.put("log4j.category.py", "info, myAppender");
		conf.put("log4j.appender.myAppender",
				"org.apache.log4j.ConsoleAppender");
		conf.put("log4j.appender.myAppender.layout",
				"org.apache.log4j.PatternLayout");
		conf.put("log4j.appender.myAppender.layout.ConversionPattern",
				"%d{ABSOLUTE} %-5p [%c{1} (%F:%M:%L)] %m%n");

		PropertyConfigurator.configure(conf);
	}

}
