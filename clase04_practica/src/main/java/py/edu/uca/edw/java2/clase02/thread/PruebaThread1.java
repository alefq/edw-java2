package py.edu.uca.edw.java2.clase02.thread;

import java.io.File;

public class PruebaThread1 extends Thread {

	/*
	 * Estas variables son cálculos útiles que sirven para usar en los sleep de
	 * un thread o para adelantar o sumar fechas
	 */
	public static final long UN_SEGUNDO = 1000;
	public static final long UN_MINUTO = UN_SEGUNDO * 60;
	public static final long UNA_HORA = UN_MINUTO * 60;
	public static final long UN_DIA = UNA_HORA * 24;

	public PruebaThread1(String nombre) {
		super(nombre);
	}

	public void run() {
		/* Obtenemos el directorio temporal del entorno */
		String dirTemporal = System.getProperty("java.io.tmpdir");
		while (true) {
			try {
				/*
				 * En un ciclo infinito contamos la cantidad de archivos en el
				 * directorio temporal
				 */
				System.out.println("Nro. de archivos en " + dirTemporal + " = "
						+ contarArchivos(dirTemporal));
				sleep(UN_SEGUNDO);
			} catch (InterruptedException e) {
				/*
				 * Si el thread recibió una interrupción, terminamos el ciclo
				 * con la sentencia "break"
				 */
				e.printStackTrace(System.err);
				System.out.println("Terminando el Thread: " + getName());
				break;
			}
		}

	}

	public int contarArchivos(String directorio) {
		/*
		 * Contamos la cantidad de archivos en la ruta al directorio pasado de
		 * parámetro
		 */
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
