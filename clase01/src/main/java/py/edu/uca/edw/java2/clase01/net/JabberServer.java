package py.edu.uca.edw.java2.clase01.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
//: c15:JabberServer.java
//From 'Thinking in Java, 2nd ed.' by Bruce Eckel
//www.BruceEckel.com. See copyright notice in CopyRight.txt.
//Very simple server that just
//echoes whatever the client sends.

public class JabberServer {
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
			while (true) {
				// Blocks until a connection occurs:
				Socket socketInConnection = serverSocket.accept();
				try {
					System.out.println("Connection accepted: "
							+ socketInConnection);
					Reader socketInputReader = new InputStreamReader(
							socketInConnection.getInputStream());
					BufferedReader inputBufferReader = new BufferedReader(
							socketInputReader);
					Writer socketOutputWriter = new OutputStreamWriter(
							socketInConnection.getOutputStream());
					Writer socketBufferedWriter = new BufferedWriter(
							socketOutputWriter);
					// Output is automatically flushed
					// by PrintWriter:
					PrintWriter serverOutputWriter = new PrintWriter(
							socketBufferedWriter, true);
					while (true) {
						String str = inputBufferReader.readLine();
						if (str.equals(JabberConstants.FIN_COMUNICACION))
							break;
						else if (str.startsWith("Cotizacion")) {

						}
						System.out.println("Echoing: " + str);
						serverOutputWriter.println(socketInConnection
								.getInetAddress() + " dice: " + str);
					}
					// Always close the two sockets...
				} finally {
					System.out.println("closing...");
					socketInConnection.close();
				}
			}
		} finally {
			serverSocket.close();
		}
	}
}