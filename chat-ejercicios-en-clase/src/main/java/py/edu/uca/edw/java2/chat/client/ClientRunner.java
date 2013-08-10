package py.edu.uca.edw.java2.chat.client;

import org.apache.log4j.xml.DOMConfigurator;

class ClientRunner {
	// I sinartisi pou kaleite gia na ani3ei o Client
	// dimiourgite antikeimeno kai trexi apo alli klasi logo provlimatwn me
	// static prosvaseis klp
	public static void main(String args[]) {
		/* Inicializamos el log4j */
		DOMConfigurator.configure("log4j.xml");

		Client c = new Client();
		c.run();
	}
}