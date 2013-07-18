package py.edu.uca.edw.java2.clase01.net;

//: c15:JabberClient.java
//From 'Thinking in Java, 2nd ed.' by Bruce Eckel
//www.BruceEckel.com. See copyright notice in CopyRight.txt.
//Very simple client that just sends
//lines to the server and reads lines
//that the server sends.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class JabberClient {
	public static void main(String[] args) {
		if (args.length != 1) {
			/*
			 * El programa debe recibir un parámetro que representa el hostname
			 * del sevidor de chat/jabber
			 */
			System.out.println("Usage: java JabberClient <hostname>");
			return;
		}
		JabberClient client = new JabberClient();
		try {
			client.connectToServer(args[0]);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connectToServer(String servidor) throws UnknownHostException,
			IOException {
		/* Obtenemos la dirección de internet del servidor */
		InetAddress serverAddress = InetAddress.getByName(servidor);

		/* Se imprime la información del servidor */
		System.out.println("addr = " + serverAddress);
		Socket socketToServer = new Socket(serverAddress, JabberServer.PORT);
		try {
			System.out.println("socket = " + socketToServer);
			BufferedReader inBufferedReaderFromServer = SocketUtil.getBufferedReader(socketToServer);

			Writer outBufferedWriterToServer = SocketUtil.getBufferedWriter(socketToServer);

			/*
			 * Como estamos trabajando con caracteres, utilizamos un PrintWriter
			 * para enviar los datos al servidor. PrintWriter se encarga de
			 * hacer las conversiones que sean necesarias entre plataformas para
			 * mostrar los caracteres según el encoding de la máquina que recibe
			 * los bytes
			 */
			PrintWriter outPrintWriterToServer = new PrintWriter(
					outBufferedWriterToServer, true);
			for (int i = 0; i < 10; i++) {
				/* Enviamos al servidor nuestra línea de chat */
				outPrintWriterToServer.println("howdy " + i);
				/* Obtenemos desde el servidor, la línea de respuesta */
				String strFromServer = inBufferedReaderFromServer.readLine();
				/* Imprimimos el mensaje recibido */
				System.out.println(strFromServer);
			}
			/* Terminamos la conversación */
			outPrintWriterToServer.println(JabberConstants.FIN_COMUNICACION);
		} finally {
			// Guard everything in a try-finally to make
			// sure that the socket is closed:
			System.out.println("closing...");
			socketToServer.close();
		}
	}


} // /:
