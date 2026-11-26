package instituto.app;

import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JOptionPane;
import instituto.modelo.*;

public class Main {

	public static void main(String args[]) {
		Ventana ventana = new Ventana();
		ventana.setVisible(false);
		try (Connection con = Conexion.getConexion()) {
		} catch (SQLException e) {
			switch (e.getErrorCode()) {
				case 1045:
					JOptionPane.showMessageDialog(ventana, "Error, contraseña o usuario equivocado.",
							"autenticacion fallida", JOptionPane.ERROR_MESSAGE);
					ventana.dispose();
					return;
				default:
					JOptionPane.showMessageDialog(ventana,
							"error al cargar la base de datos, consulte con su tecnico de confianza e intente nuevamente",
							"error: base de datos", JOptionPane.ERROR_MESSAGE);
					ventana.dispose();
					return;
			}
		}
		ventana.setVisible(true);
	}
}
