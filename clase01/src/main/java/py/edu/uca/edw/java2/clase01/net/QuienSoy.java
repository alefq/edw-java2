package py.edu.uca.edw.java2.clase01.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class QuienSoy {
	public static void main(String[] args) {
		try {
			InetAddress addr = InetAddress.getLocalHost();

			/* Se obtiene la dirección de IP */
			System.out.println("Nro. de IP " + addr.getHostAddress());

			
			Thread.sleep(5000);
			
			/* Se obtiene el nombre de esta máquina */
			String hostname = addr.getHostName();
			System.out.println("hostname: " + hostname);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
