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
		Connection conPostgres = null;
		try {

			Drivers.cargarDrivers();
			// Connection conOracle =
			// ConnectionUtil.obtenerConexion(ConnectionUtil.DBMS_TYPE_ORACLE);
			conPostgres = ConnectionUtil
					.obtenerConexion(ConnectionUtil.DBMS_TYPE_POSTGRES);
			pruebaSelect(conPostgres);
//			pruebaInsert(conPostgres);
//			pruebaSelect(conPostgres);
//			pruebaUpdate(conPostgres);
//			pruebaDelete(conPostgres);

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
		Statement stmt = conPostgres.createStatement();

		// Step 3 & 4: execute a SQL UPDATE statement via executeUpdate()
		// which returns an int indicating the number of row affected.
		String sqlStr = "update bitacora_servicios set email = 'webmaster@uc.edu.py', estado = 'CAIDO' where id_bitacora_servicios = 2";
		System.out.println("El SQL es: " + sqlStr); // For debugging
		int count = stmt.executeUpdate(sqlStr);
		System.out.println(count + " registros modificados");
	}

	private static void pruebaSelect(Connection conPostgres)
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

	private static void pruebaInsert(Connection conPostgres)
			throws SQLException {
		String sqlInsert = "insert into bitacora_servicios "
				+ "(id_bitacora_servicios, alias, direccion_ip, puerto, estado, email) "
				+ "values(?,?,?,?,?,?)";
		PreparedStatement pstmt = conPostgres.prepareStatement(sqlInsert);
		pstmt.setInt(1, 4);
		pstmt.setString(2, "chat");
		pstmt.setString(3, "10.1.1.3");
		pstmt.setInt(4, 5222);
		pstmt.setNull(5, Types.VARCHAR);
		pstmt.setString(6, "algo@gmail.com");
		pstmt.executeUpdate();
	}

	private static void pruebaDelete(Connection conPostgres)
			throws SQLException {
		Statement stmt = conPostgres.createStatement();

		// Step 3 & 4: execute a SQL UPDATE statement via executeUpdate()
		// which returns an int indicating the number of row affected.
		String sqlDelete = "delete from bitacora_servicios where id_bitacora_servicios = 4";
		System.out.println("El SQL es: " + sqlDelete); // For debugging
		int count = stmt.executeUpdate(sqlDelete);
		System.out.println(count + " registros modificados");
	}

}
