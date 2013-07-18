package py.edu.uca.edw.java2.clase01.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

public class SocketUtil {
	public static Writer getBufferedWriter(Socket socketToServer)
			throws IOException {
		/*
		 * Como nuestro stream es de caracteres, utilizamos un decorator/wrapper
		 * de tipo Writer
		 */
		Writer outWriterToServer = new OutputStreamWriter(
				socketToServer.getOutputStream());
		/*
		 * Para hacer uso de buffers para optimizar la comunicación, lo volvemos
		 * a encapsular en un BufferedWriter
		 */
		Writer outBufferedWriterToServer = new BufferedWriter(outWriterToServer);
		return outBufferedWriterToServer;
	}

	public static BufferedReader getBufferedReader(Socket socketToServer)
			throws IOException {
		/*
		 * Reader es la pareja de la clase Writer, se usa para leer datos de
		 * tipo caracter
		 */
		Reader inputReaderFromServer = new InputStreamReader(
				socketToServer.getInputStream());
		/* Utilizamos un buffer para leer */
		BufferedReader inBufferedReaderFromServer = new BufferedReader(
				inputReaderFromServer);
		return inBufferedReaderFromServer;
	}
}
