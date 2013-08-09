package py.edu.uca.edw.java2.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

//Vasiki klasi gia to Server.
//perimeni sindesis kai dimiourga ena ClientThread gia to kathena 
//pou xeirizete tin epikoinonia mazi tou
//kai perimeno allo connection

class Server {
	// Vector gia apothikefsi twn thread twn xristwn
	static Vector clients;
	// To socket gia ti epikoinonia
	static Socket clientSocket;

	public static void main(String args[]) {
		// arxikopoiiseis
		clients = new Vector();

		clientSocket = null;

		// Server socket
		ServerSocket serverSocket = null;

		try {
			// arxikopoiisi tou Server socket sto port 9999
			serverSocket = new ServerSocket(9999);
		} catch (IOException e) {
			System.out.println("IO " + e);
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
				System.out.println("IOaccept " + e);

			}
		}
	}
}