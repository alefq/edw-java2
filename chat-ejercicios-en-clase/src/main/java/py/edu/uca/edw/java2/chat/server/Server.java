package py.edu.uca.edw.java2.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import py.edu.uca.edw.java2.chat.i18n.Messages;

//Vasiki klasi gia to Server.
//perimeni sindesis kai dimiourga ena ClientThread gia to kathena 
//pou xeirizete tin epikoinonia mazi tou
//kai perimeno allo connection

class Server {
	static Logger log = Logger.getLogger(Server.class);

	// Vector gia apothikefsi twn thread twn xristwn
	static Vector clients;
	// To socket gia ti epikoinonia
	static Socket clientSocket;

	public static void main(String args[]) {
		/* Inicializamos el log4j */
		DOMConfigurator.configure("log4j.xml");

		int puerto = 9999;
		
		// arxikopoiiseis
		clients = new Vector();

		clientSocket = null;

		// Server socket
		ServerSocket serverSocket = null;

		try {
			// arxikopoiisi tou Server socket sto port 9999
			serverSocket = new ServerSocket(puerto);
			log.info(Messages.getString("Server.1") + puerto); //$NON-NLS-1$
		} catch (IOException e) {
			log.error("IO " + e); //$NON-NLS-1$
		}

		// perimeno gia sindeseiis kai se kathe sindesi
		// dimiourgo ena neo antikeimeno ClientThread pou tin xeirizete
		// to energopoio
		// kai to prostheto sto vector me tous xristes
		while (true) {
			try {
				// perimeno sindesi
				clientSocket = serverSocket.accept();
				// arxikopoiisi tou ClientThread
				// Constructor me sockets
				ClientThread clientThread = new ClientThread(clientSocket);
				// prostheto sto vector
				// INE apli sindesi oxi xristis
				// se afto to simio
				// ginete xristis otan kani valid login
				clients.add(clientThread);
				clientThread.start();
			} catch (IOException e) {
				log.error("IOaccept " + e); //$NON-NLS-1$

			}
		}
	}
}