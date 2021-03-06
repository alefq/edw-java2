package py.edu.uca.edw.java2.clase03.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import py.edu.uca.edw.java2.clase03.db.util.ConnectionUtil;

public class PruebasBD {

	public PruebasBD() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Connection conexionABD = null;
		try {

			Drivers.cargarDrivers();
			// Connection conOracle =
			// ConnectionUtil.obtenerConexion(ConnectionUtil.DBMS_TYPE_ORACLE);
			conexionABD = ConnectionUtil
					.obtenerConexion(ConnectionUtil.DBMS_TYPE_POSTGRES);
//			 conexionABD = ConnectionUtil
//			 .obtenerConexion(ConnectionUtil.DBMS_TYPE_MYSQL);
//			 pruebaSelectMysql(conexionABD);
			pruebaSelectPostgresql(conexionABD);
			// pruebaInsert(conexionABD);
//			pruebaSelectPostgresql(conexionABD);
			// pruebaUpdate(conexionABD);
//			pruebaDeletePreparedStatement(conexionABD);

		} catch (ClassNotFoundException e) {
			System.out.println("No se encontro el driver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("No se pudo conectar" + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void pruebaUpdate(Connection conPostgres)
			throws SQLException {

		// Step 3 & 4: execute a SQL UPDATE statement via executeUpdate()
		// which returns an int indicating the number of row affected.
		String sqlStr = "update bitacora_servicios set email = ?, estado = ? where id_bitacora_servicios = ?";
		PreparedStatement statement = conPostgres.prepareStatement(sqlStr);
		statement.setString(1, "webmaster@uc.edu.py");
		statement.setString(2, "OK");
		statement.setInt(3, 3);

		System.out.println("El SQL es: " + sqlStr); // For debugging
		int count = statement.executeUpdate();
		System.out.println(count + " registros modificados");
	}

	private static void pruebaSelectPostgresql(Connection conPostgres)
			throws SQLException {
		Statement sentencia = conPostgres.createStatement();
		ResultSet rs = sentencia
				.executeQuery("select * from bitacora_servicios");

		while (rs.next()) {
			System.out.println("Alias " + rs.getString("alias"));
			System.out.println("Dirección " + rs.getString("direccion_ip"));

			System.out.println("puerto " + rs.getInt("puerto"));
			System.out.println("estado " + rs.getString("estado"));
			System.out.println("--- --- ---");
		}
	}

	private static void pruebaSelectMysql(Connection conPostgres)
			throws SQLException {
		Statement sentencia = conPostgres.createStatement();
		ResultSet rs = sentencia.executeQuery("select * from usuario");

		while (rs.next()) {
			System.out.println("Nombre " + rs.getString("nombre"));
			System.out.println("Apellido " + rs.getString("apellidos"));
			System.out.println("--- --- ---");
		}
	}

	private static void pruebaInsert(Connection conPostgres)
			throws SQLException {
		String sqlInsert = "insert into bitacora_servicios "
				+ "(id_bitacora_servicios, alias, direccion_ip, puerto, estado, email) "
				+ "values(?,?,?,?,?,?)";
		PreparedStatement pstmt = conPostgres.prepareStatement(sqlInsert);
		pstmt.setInt(1, 6);
		String alias = "";
		pstmt.setString(2, alias);
		pstmt.setString(3, "10.1.1.3");
		pstmt.setInt(4, 5222);
		pstmt.setNull(5, Types.VARCHAR);
		pstmt.setString(6, "algo@gmail.com");
		int insertados = pstmt.executeUpdate();
		if (insertados > 0) {

			System.out
					.println("Los datos se insertaron correctamente a la base de datos.");
		}
	}

	private static void pruebaDeleteStatement(Connection conPostgres)
			throws SQLException {
		Statement stmt = conPostgres.createStatement();

		// Step 3 & 4: execute a SQL UPDATE statement via executeUpdate()
		// which returns an int indicating the number of row affected.
		String sqlDelete = "delete from bitacora_servicios where id_bitacora_servicios = 4";
		System.out.println("El SQL es: " + sqlDelete); // For debugging
		int count = stmt.executeUpdate(sqlDelete);
		System.out.println(count + " registros modificados");
	}

	private static void pruebaDeletePreparedStatement(Connection conPostgres)
			throws SQLException {

		String sqlDelete = "delete from bitacora_servicios where id_bitacora_servicios = ?";
		PreparedStatement preparedStatement = conPostgres
				.prepareStatement(sqlDelete);
		preparedStatement.setInt(1, 5);
		System.out.println("El SQL es: " + sqlDelete); // For debugging
		int count = preparedStatement.executeUpdate(sqlDelete);
		System.out.println(count + " registros modificados");
	}

}
