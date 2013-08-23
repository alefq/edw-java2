package py.edu.uca.edw.java2.chat.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import py.edu.uca.edw.java2.chat.exception.PersistenceException;
import py.edu.uca.edw.java2.chat.util.ConnectionUtil;
import py.edu.uca.edw.java2.chat.util.Drivers;

public class AuditoriaDAO {

	Connection conexionABD = null;

	Logger log = Logger.getLogger(AuditoriaDAO.class);

	public void guardarLogin(String string) throws PersistenceException {
		// TODO Auto-generated method stub
		log.debug("Se audita: " + string);
		inicializarConexion();
	}

	public void inicializarConexion() {
		try {
			Drivers.cargarDrivers();
			conexionABD = ConnectionUtil
					.obtenerConexion(ConnectionUtil.DBMS_TYPE_POSTGRES);
			pruebaSelectPostgresql(conexionABD);
		} catch (ClassNotFoundException e) {
			log.error("No se encontro el driver", e);
		} catch (SQLException e) {
			log.error("No se pudo conectar a la base de datos", e);
		}
	}

	public void pruebaSelectPostgresql(Connection conPostgres)
			throws SQLException {
		Statement sentencia = conPostgres.createStatement();
		ResultSet rs = sentencia
				.executeQuery("SELECT chat_audit_id, nickname, tipo, fecha FROM conexion_audit");

		while (rs.next()) {
			System.out.println("Alias " + rs.getInt("chat_audit_id"));
			System.out.println("Dirección " + rs.getString("nickname"));

			System.out.println("puerto " + rs.getString("tipo"));
			System.out.println("estado " + rs.getDate("fecha"));
			System.out.println("--- --- ---");
		}
	}

}
