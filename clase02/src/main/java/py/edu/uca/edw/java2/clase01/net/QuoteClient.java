package py.edu.uca.edw.java2.clase01.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class QuoteClient {
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.out.println("Usage: java QuoteClient <hostname>");
			return;
		}
		QuoteClient client = new QuoteClient();
		client.readUdpSocket(args[0]);
	}

	public void readUdpSocket(String hostname) throws SocketException,
			UnknownHostException, IOException {
		// get a datagram socket
		DatagramSocket socket = new DatagramSocket();

		// send request
		byte[] buf = new byte[256];
		InetAddress address = InetAddress.getByName(hostname);
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address,
				4445);
		socket.send(packet);

		// get response
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);

		// display response
		String received = new String(packet.getData());
		System.out.println("Quote of the Moment: " + received);

		socket.close();
	}
}