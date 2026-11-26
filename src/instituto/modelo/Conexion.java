package instituto.modelo;

import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {
	private final static String DBURL;
	private final static String USER;
	private final static String PASSWORD;

	static {
		String url = "";
		String user = "";
		String password = "";
		try (BufferedReader lector = new BufferedReader(new FileReader(".env"))) {
			url = lector.readLine().trim();
			user = lector.readLine().trim();
			password = lector.readLine().trim();
		} catch (IOException e) {
			System.out.println("Error al leer el archivo .env, terminando programa");
		}
		DBURL = url;
		USER = user;
		PASSWORD = password;
	}

	public static Connection getConexion() throws SQLException {
		return DriverManager.getConnection(DBURL, USER, PASSWORD);
	}

}
