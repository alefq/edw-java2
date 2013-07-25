package py.edu.uca.edw.java2.clase01.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class JabberServer {
	/* Definimos el servidor en el que correrá nuestro servidor de chat */
	// Choose a port outside of the range 1-1024:
	public static final int PORT = 8080;

	public static void main(String[] args) throws IOException {
		JabberServer server = new JabberServer();
		server.start();
	}

	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Started: " + serverSocket);
		try {
			/*
			 * En un ciclo infinito, se aceptan conexiones, una detrás de otra.
			 * Por la implementación de este servidor, es necesario que termine
			 * una conexión para poder aceptar la siguiente.
			 */
			while (true) {
				/* Se bloquea hasta que se acepte una conexión */
				Socket socketFromClient = serverSocket.accept();
				try {
					System.out.println("Connection accepted: "
							+ socketFromClient);
					BufferedReader inputBufferReader = SocketUtil
							.getBufferedReader(socketFromClient);
					Writer socketOutputWriter = new OutputStreamWriter(
							socketFromClient.getOutputStream());
					Writer socketBufferedWriter = new BufferedWriter(
							socketOutputWriter);
					/*
					 * Como estamos trabajando con caracteres, utilizamos un
					 * PrintWriter para enviar los datos al servidor.
					 * PrintWriter se encarga de hacer las conversiones que sean
					 * necesarias entre plataformas para mostrar los caracteres
					 * según el encoding de la máquina que recibe los bytes
					 */
					PrintWriter serverOutputWriter = new PrintWriter(
							socketBufferedWriter, true);
					while (true) {
						/* El string enviado por el cliente */
						String stringFromCLient = inputBufferReader.readLine();
						if (stringFromCLient
								.equals(JabberConstants.FIN_COMUNICACION)) {
							/*
							 * Cuándo recibimos el FIN_COMUNICACION salimos del
							 * bucle infinito y termina la comunicación
							 */
							break;
						} else if (stringFromCLient
								.startsWith(JabberConstants.FIN_COTIZACION)) {
							/*
							 * Esto sería si nuestro protocolo acepta comandos,
							 * por ej. aquí podríamos retornar la cotización de
							 * monedas
							 */
						}
						System.out.println("Echoing: " + stringFromCLient);
						serverOutputWriter.println(socketFromClient
								.getInetAddress()
								+ " dice: "
								+ stringFromCLient);
					}
					/* Siempre hay que cerrar ambos sockets */
				} finally {
					System.out.println("closing...");
					socketFromClient.close();
				}
			}
		} finally {
			serverSocket.close();
		}
	}
}