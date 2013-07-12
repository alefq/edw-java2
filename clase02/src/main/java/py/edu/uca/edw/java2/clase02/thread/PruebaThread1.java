package py.edu.uca.edw.java2.clase02.thread;

import java.io.File;

public class PruebaThread1 extends Thread {

	public static final long UN_SEGUNDO = 1000;
	public static final long UN_MINUTO = UN_SEGUNDO * 60;
	public static final long UNA_HORA = UN_MINUTO * 60;
	public static final long UN_DIA = UNA_HORA * 24;

	
	private int contador = 5;

	public PruebaThread1(String nombre) {
		super(nombre);
	}

	public void run() {
		String dirTemporal = System.getProperty("java.io.tmpdir");
		while (true) {
			try {
				System.out.println("Nro. de archivos en " + dirTemporal + " = "
						+ contarArchivos(dirTemporal));
				sleep(UN_SEGUNDO);
			} catch (InterruptedException e) {
				e.printStackTrace(System.err);
			}
		}

	}

	public int contarArchivos(String directorio) {
		int nroArchivos = 0;
		File file = new File(directorio);
		if (file.isDirectory()) {
			String files[] = file.list();
			nroArchivos = files.length;
		} else {
			System.out.println("El archivo " + directorio.toString()
					+ " no es un directorio");
		}
		return nroArchivos;
	}
}
