/*
 * Created on 18-nov-2005
 *
 */
package py.edu.uca.edw.java2.clase03.db.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import py.edu.uca.edw.java2.clase03.db.Drivers;
import py.edu.uca.edw.java2.clase03.db.PropertiesClaveValor;

public class ConnectionUtil {

	private static final String HOST_NAME = "HostName";
	final public static int DBMS_TYPE_POSTGRES = 0;
	final public static int DBMS_TYPE_ORACLE = 1;
	final public static int DBMS_TYPE_ODBC_ORACLENATI = 2;
	final public static int DBMS_TYPE_ODBC_EXCEL = 3;
	final public static int DBMS_TYPE_ODBC_TXT = 4;
	final public static int DBMS_TYPE_MYSQL = 5;

	/*
	 * La ruta hasta el archivo .properties que contiene los datos para
	 * conectarnos a la Base de Datos
	 */
	private static final String POSTGRES_PROPERTIES_PATH = "postgres.properties";
	private static final String MYSQL_PROPERTIES_PATH = "mysql.properties";

	public static void main(String[] args) {
		try {
			/* Se inicializan los controladores necesarios */
			Drivers.cargarDrivers();

			// Connection c =
			// obtenerConexion(ConnectionUtil.DBMS_TYPE_ODBC_EXCEL);
			/* Obtenemos una conexión de tipo PostgreSQL */
			Connection bdConeccion = obtenerConexion(ConnectionUtil.DBMS_TYPE_POSTGRES);

			System.out.println("Hacemos algo para verificar la conexion: "
					+ bdConeccion.getAutoCommit());

			/* Metadatos */
			DatabaseMetaData dbMetadatos = bdConeccion.getMetaData();
			/* Nombre del producto */
			System.out.println("DB PRODUCT NAME: "
					+ dbMetadatos.getDatabaseProductName());

			/* tenemos que cerrar si todo anda bien. */
			closeConnection(bdConeccion);

		} catch (SQLException e) {
			/* hubo un error al conectarse */
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			/* no se encontró la clase del driver */
			e.printStackTrace();
		}
	}

	/* Devuelve la conexión de acuerdo tipo de DBMS (Data base managment system) */
	public static Connection obtenerConexion(int DBMS_TYPE) throws SQLException {
		String url;
		Connection conexion = null;
		Properties propiedadesDeConexion = null; 
		String rutaAlProperties = null;
		switch (DBMS_TYPE) {
		case DBMS_TYPE_ORACLE:
			// Properties prop = new Properties();

			PropertyResourceBundle prop = (PropertyResourceBundle) PropertyResourceBundle
					.getBundle("Connection");
			// prop.load(new
			// FileInputStream(getClass().getClassLoader().getResource(Globals.ARCHIVO_XML_TRADUCCION).getFile());

			String hostName = (String) prop.getString(HOST_NAME);
			String sid = (String) prop.getString("SID");
			String port = (String) prop.getString("Port");
			String userName = (String) prop.getString("UserName");
			String pass = (String) prop.getString("Password");

			url = "jdbc:oracle:thin:@" + hostName + ":" + port + ":" + sid;
			conexion = DriverManager.getConnection(url, userName, pass);
			break;
		case DBMS_TYPE_POSTGRES:
			conexion = obtenerConexion(POSTGRES_PROPERTIES_PATH);
			break;
		case DBMS_TYPE_ODBC_ORACLENATI:
			url = "jdbc:odbc:OracleNati";
			conexion = DriverManager.getConnection(url, "soaa", "faute");
			break;
		case DBMS_TYPE_MYSQL:
			conexion = obtenerConexion(MYSQL_PROPERTIES_PATH);

			break;
		case DBMS_TYPE_ODBC_EXCEL:
			url = "jdbc:odbc:PruebaExcel";
			conexion = DriverManager.getConnection(url);
			break;
		case DBMS_TYPE_ODBC_TXT:
			url = "jdbc:odbc:TextFiles";
			conexion = DriverManager.getConnection(url);
			break;
		}
		return conexion;
	}

	public static Connection obtenerConexion(String rutaAlProperties)
			throws SQLException {
		Connection conexion;
		Properties propiedadesDeConexion;
		/*
		 * Cargamos las propiedades con los valores necesarios para
		 * conectarnos al PosgreSQL
		 */
		propiedadesDeConexion = PropertiesClaveValor
				.cargarProperties(rutaAlProperties);

		/* El URL para la conexión jdbc */
		String url2 = propiedadesDeConexion.getProperty("Url");
		/*
		 * El puerto (sólo necesario si se utilizará un puerto distinto del
		 * estándar para el motor, en el caso del PostgreSQL el puerto
		 * estándar es el 5432)
		 */
		String port2 = propiedadesDeConexion.getProperty("Driver");
		/* El usuario para conectarse a la base de datos */
		String userName2 = propiedadesDeConexion.getProperty("UserName");
		/* La contraseña para la conexión a la BD */
		String pass2 = propiedadesDeConexion.getProperty("Password");

		/*
		 * DriverManager es una clase de JAVA que establece la conexión a la
		 * BD según los parámetros que le pasamos
		 */
		conexion = DriverManager.getConnection(url2, userName2, pass2);
		return conexion;
	}

	/*
	 * Luego de finalizar el uso de una conexión debe cerrarse para liberar el
	 * recurso
	 */
	public static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
