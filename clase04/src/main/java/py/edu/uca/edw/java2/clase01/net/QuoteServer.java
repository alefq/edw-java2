package py.edu.uca.edw.java2.clase01.net;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class QuoteServer {
	public static void main(String[] args) throws IOException {
		new QuoteServerThread().start();
	}
}

class QuoteServerThread extends Thread {
	private static final String FILE_COTIZACIONES = Messages.getString("QuoteServer.filename"); //$NON-NLS-1$

	/* Se define un inner class que será el servidor corriendo en un thread */
	private DatagramSocket socket = null;

	private DataInputStream inStream = null;

	private boolean moreQuotes = true;

	public QuoteServerThread() throws IOException {
		super(Messages.getString("QuoteServer.threadname")); //$NON-NLS-1$
		socket = new DatagramSocket(4445);
		try {
			/*
			 * Desde dónde se leerían las cotizaciones, en este caso enviamos
			 * cotizaciones de monedas como quotes
			 */
			inStream = new DataInputStream(new FileInputStream(
					FILE_COTIZACIONES));
		} catch (FileNotFoundException e) {
			/*
			 * Si no hay archivo de cotizaciones, sólo se responderá con la hora
			 * actual
			 */
			System.err
					.println(Messages.getString("QuoteServer.errormessage_filenotfound")); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("deprecation")
	public void run() {
		/* El thread correrá mientras queden quotes */
		while (moreQuotes) {
			try {
				/* Definimos el buffer para enviar los quotes */
				byte[] buffer = new byte[256];

				/* Recibimos los paquetes desde el cliente */
				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(packet);

				/* Vemos con que responder */
				String cotizacionOFecha = null;
				if (inStream == null)
					cotizacionOFecha = new Date().toString();
				else
					cotizacionOFecha = getNextQuote();
				cotizacionOFecha.getBytes(0, cotizacionOFecha.length(), buffer,
						0);

				/* Enviar la respuesta al cliente address:port */
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buffer, buffer.length, address,
						port);
				socket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
				moreQuotes = false;
			}
		}
		socket.close();
	}

	@SuppressWarnings("deprecation")
	private String getNextQuote() {
		String returnValue = null;
		try {
			if ((returnValue = inStream.readLine()) == null) {
				inStream.close();
				moreQuotes = false;
				returnValue = Messages.getString("QuoteServer.msg_goodbye"); //$NON-NLS-1$
			}
		} catch (IOException e) {
			returnValue = Messages.getString("QuoteServer.errro_in_server"); //$NON-NLS-1$
		}
		return returnValue;
	}

}