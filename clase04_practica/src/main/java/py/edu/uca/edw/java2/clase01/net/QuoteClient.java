package py.edu.uca.edw.java2.clase01.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class QuoteClient {
	public static void main(String[] args) throws IOException {
		/* Ejempl de un cliente UDP que lee quotes (citas) de un servidor */
		if (args.length != 1) {
			/*
			 * El programa debe recibir un parámetro que representa el hostname
			 * del sevidor de Quotes
			 */
			System.out.println("Usage: java QuoteClient <hostname>");
			return;
		}
		QuoteClient client = new QuoteClient();
		client.readUdpSocket(args[0]);
	}

	public void readUdpSocket(String hostname) throws SocketException,
			UnknownHostException, IOException {
		/* Obtenemos un socket de tipo Datagram */
		DatagramSocket socket = new DatagramSocket();

		/* Enviamos un request */
		byte[] buf = new byte[256];
		InetAddress address = InetAddress.getByName(hostname);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address,
				4445);
		socket.send(packet);

		/* obtenemos la respuesta */
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);

		/* mostramos el resultado */
		String received = new String(packet.getData());
		System.out.println("Quote of the Moment: " + received);

		/* cerramos el socket */
		socket.close();
	}
}