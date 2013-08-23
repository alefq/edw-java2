package py.edu.uca.edw.java2.chat.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.log4j.Logger;

import py.edu.uca.edw.java2.chat.exception.PersistenceException;
import py.edu.uca.edw.java2.chat.util.ConnectionUtil;
import py.edu.uca.edw.java2.chat.util.Drivers;

public class AuditoriaDAO {

	Connection conexionABD = null;

	Logger log = Logger.getLogger(AuditoriaDAO.class);

	public AuditoriaDAO() {
		inicializarConexion();
	}

	public void guardarLogin(String nickname) throws PersistenceException {
		// TODO Auto-generated method stub
		log.debug("Se audita: " + nickname);
		try {
			persistirLogin(conexionABD, nickname);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	public void inicializarConexion() {
		try {
			Drivers.cargarDrivers();
			conexionABD = ConnectionUtil
					.obtenerConexion(ConnectionUtil.DBMS_TYPE_POSTGRES);
			// pruebaSelectPostgresql(conexionABD);
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
				.executeQuery("SELECT conexion_audit_id, nickname, tipo, fecha FROM conexion_audit");

		while (rs.next()) {
			log.info("ID " + rs.getInt("conexion_audit_id"));
			log.info("Nickname" + rs.getString("nickname"));

			log.info("Tipo Conexion" + rs.getString("tipo"));
			log.info("Fecha " + rs.getDate("fecha"));
			log.info("--- --- ---");
		}
	}

	public void persistirLogin(Connection conPostgres, String nickname)
			throws SQLException {
		String sqlInsert = "INSERT INTO conexion_audit(" + "nickname, tipo)"
				+ "VALUES (?, ?);";
		PreparedStatement pstmt = conPostgres.prepareStatement(sqlInsert);
		pstmt.setString(1, nickname);
		pstmt.setString(2, "LOGIN");
		int insertados = pstmt.executeUpdate();
		if (insertados > 0) {

			System.out
					.println("Los datos se insertaron correctamente a la base de datos.");
		}
	}
}
