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
	private DatagramSocket socket = null;

	private DataInputStream in = null;

	private boolean moreQuotes = true;

	public QuoteServerThread() throws IOException {
		super("QuoteServerThread");
		socket = new DatagramSocket(4445);
		try {
			in = new DataInputStream(new FileInputStream("one-liners.txt"));
		} catch (FileNotFoundException e) {
			System.err
					.println("Could not open quote file. Serving time instead.");
		}
	}

	@SuppressWarnings("deprecation")
	public void run() {

		while (moreQuotes) {
			try {
				byte[] buf = new byte[256];

				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				// figure out response
				String dString = null;
				if (in == null)
					dString = new Date().toString();
				else
					dString = getNextQuote();
				dString.getBytes(0, dString.length(), buf, 0);

				// send the response to the client at "address" and "port"
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, address, port);
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
			if ((returnValue = in.readLine()) == null) {
				in.close();
				moreQuotes = false;
				returnValue = "No more quotes. Goodbye.";
			}
		} catch (IOException e) {
			returnValue = "IOException occurred in server.";
		}
		return returnValue;
	}

}