<<<<<<< HEAD
public class Conexion {
=======
package instituto.modelo;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.sql.*;

public class Conexion {
	private final static String DBURL;
	private final static String USER;
	private final static String PASSWORD;

	static {
		try (BufferedReader lector = new BufferedReader(new FileReader(".env"))) {
			DBURL = lector.readLine().trim();
			USER = lector.readLine().trim();
			PASSWORD = lector.readLine().trim();
		} catch (IOException e) {
			throw new Error("Tu archivo .env esta mal. Cerrando programa");
		}
	}

	public static Connection getConexion() throws SQLException {
		return DriverManager.getConnection(DBURL, USER, PASSWORD);
	}
>>>>>>> 322c3073fc13b6d87f21645a9989dd0a906c3328

}
