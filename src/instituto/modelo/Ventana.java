package instituto.modelo;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.SQLException;
import java.util.concurrent.Flow;

public class Ventana extends JFrame {
	private DefaultTableModel mAlumno;
	private JScrollPane panelTabla;
	private JPanel panelBotones;

	public Ventana() {
		super("Instituto");
		cargarVentana();
		cargarTabla();
		cargarBotonAlta();
		cargarBotonInscripcion();
		cargarBotonBaja();
		cargarPanelCentral();
		setVisible(true);
	}

	private void cargarPanelCentral() {
		setLayout(new BorderLayout(10, 10));
		add(panelTabla, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
	}

	private void cargarVentana() {
		setSize(640, 360);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
	}

	private void cargarTabla() {
		mAlumno = new DefaultTableModel(new String[] { "Curso", "Cupo", "Inscriptos", "Vacantes" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columna) {
				return columna > 0 ? Integer.class : String.class;
			}
		};
		for (int i = 0; i < 100; i++) {
			mAlumno.addRow(new Object[] { "1", "2", "3", "4" });
		}
		JTable tabla = new JTable(mAlumno);
		tabla.setAutoCreateRowSorter(true);
		panelTabla = new JScrollPane(tabla);
	}

	private void cargarBotonAlta() {
		JButton botonAlta = new JButton("Alta Alumno");
		botonAlta.addActionListener(e -> {
			JFrame formulario = formularioAlta();
		});
		panelBotones.add(botonAlta);
	}

	private JFrame formularioAlta() {
		JFrame formulario = new JFrame("Formulario");
		formulario.setSize(640, 360);
		formulario.setLocationRelativeTo(this);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new Label("DNI"));
		JTextField tDni = new JTextField();
		panel.add(tDni);
		panel.add(new Label("Nombre"));
		JTextField tNombre = new JTextField();
		panel.add(tNombre);
		panel.add(new Label("Apellido"));
		JTextField tApellido = new JTextField();
		panel.add(tApellido);
		panel.add(new Label("Correo (opcional)"));
		JTextField tCorreo = new JTextField();
		panel.add(tCorreo);
		JPanel panelBoton = new JPanel();
		panelBoton.setLayout(new FlowLayout());
		JButton aceptar = new JButton("Aceptar");
		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(e -> {
			formulario.dispose();
		});
		aceptar.addActionListener(e -> {
			String nombre = tNombre.getText().trim();
			String dni = tDni.getText().trim();
			String apellido = tApellido.getText().trim();
			String correo = tCorreo.getText().trim();
			if (dni.isEmpty()) {
				JOptionPane.showMessageDialog(formulario, "El alumno tiene que tener dni", "Error : dni alumno",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (nombre.isEmpty()) {
				JOptionPane.showMessageDialog(formulario, "El alumno tiene que tener nombre", "Error : nombre alumno",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (apellido.isEmpty()) {
				JOptionPane.showMessageDialog(formulario, "El alumno tiene que tener apellido",
						"Error : apellido alumno",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		});
		panelBoton.add(aceptar);
		panelBoton.add(cancelar);
		panel.add(panelBoton);
		formulario.add(panel);
		formulario.setVisible(true);
		return formulario;
	}

	private void cargarBotonInscripcion() {
		JButton botonInscripcion = new JButton("Inscribir");
		botonInscripcion.addActionListener(e -> {
			JFrame ventana = formularioInscripcion();
		});
		panelBotones.add(botonInscripcion);
	}

	private JFrame formularioInscripcion() {
		JFrame ventana = new JFrame("Inscripcion");
		ventana.setSize(640, 360);
		ventana.setLocationRelativeTo(this);
		JPanel panelLabel = new JPanel();
		JPanel panel = new JPanel();
		panelLabel.setLayout(new FlowLayout(1, 30, 30));
		panel.setLayout(new FlowLayout(1, 10, 10));
		JComboBox<String> alumnos = new JComboBox<>(new String[] { "Juan Gomez" });
		JComboBox<String> cursos = new JComboBox<>(new String[] { "ING-01-M" });
		JButton inscribir = new JButton("Inscribir");
		inscribir.addActionListener(e -> {
			String alumno = (String) alumnos.getSelectedItem();
			String curso = (String) cursos.getSelectedItem();
		});
		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(e -> ventana.dispose());
		panelLabel.add(new JLabel("Alumno"));
		panelLabel.add(new JLabel("Curso"));
		panel.add(alumnos);
		panel.add(cursos);
		ventana.add(panelLabel, BorderLayout.NORTH);
		ventana.add(panel, BorderLayout.CENTER);
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout());
		panelBotones.add(inscribir);
		panelBotones.add(cancelar);
		ventana.add(panelBotones, BorderLayout.SOUTH);
		ventana.setVisible(true);
		return ventana;
	}

	private void cargarBotonBaja() {
		JButton botonBaja = new JButton("Baja inscripcion");
		panelBotones.add(botonBaja);
	}

	public static void main(String args[]) {
		Ventana ventana = new Ventana();
	}
}
