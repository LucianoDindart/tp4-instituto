package instituto.app;

import javax.swing.JOptionPane;
import instituto.modelo.*;

public class Main {

	public static void main(String args[]) {
		Ventana ventana = new Ventana();
		try {
			ventana.setVisible(false);
			Conexion.getConexion();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(ventana,
					"error al cargar la base de datos, consulte con su tecnico de confianza e intente nuevamente",
					"error: base de datos", JOptionPane.ERROR_MESSAGE);
			ventana.dispose();
			return;
		}
		ventana.setVisible(true);
	}
}
