package py.edu.uca.edw.java2.clase01.net;

//: c15:JabberClient.java
//From 'Thinking in Java, 2nd ed.' by Bruce Eckel
//www.BruceEckel.com. See copyright notice in CopyRight.txt.
//Very simple client that just sends
//lines to the server and reads lines
//that the server sends.
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class JabberClient {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java JabberClient <hostname>");
			return;
		}
		JabberClient client = new JabberClient();
		try {
			client.connectToServer(args);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connectToServer(String[] args) throws UnknownHostException,
			IOException {
		// Passing null to getByName() produces the
		// special "Local Loopback" IP address, for
		// testing on one machine w/o a network:
		InetAddress serverAddress = InetAddress.getByName(args[0]);
		// Alternatively, you can use
		// the address or name:
		// InetAddress addr =
		// InetAddress.getByName("127.0.0.1");
		// InetAddress addr =
		// InetAddress.getByName("localhost");
		System.out.println("addr = " + serverAddress);
		Socket socketToServer = new Socket(serverAddress, JabberServer.PORT);
		// Guard everything in a try-finally to make
		// sure that the socket is closed:
		try {
			System.out.println("socket = " + socketToServer);
			Reader inputReaderFromServer = new InputStreamReader(
					socketToServer.getInputStream());
			BufferedReader inBufferedReaderFromServer = new BufferedReader(
					inputReaderFromServer);
			
			Writer outWriterToServer = new OutputStreamWriter(
					socketToServer.getOutputStream());
			Writer outBufferedWriterToServer = new BufferedWriter(
					outWriterToServer);
			
			// Output is automatically flushed
			// by PrintWriter:
			PrintWriter outPrintWriterToServer = new PrintWriter(
					outBufferedWriterToServer, true);
			for (int i = 0; i < 10; i++) {
				outPrintWriterToServer.println("howdy " + i);
				String str = inBufferedReaderFromServer.readLine();
				System.out.println(str);
			}
			outPrintWriterToServer.println(JabberConstants.FIN_COMUNICACION);
		} finally {
			System.out.println("closing...");
			socketToServer.close();
		}
	}
} // /:
