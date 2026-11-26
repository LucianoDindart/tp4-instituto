package instituto.modelo;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.SQLException;
import java.util.concurrent.Flow;
import java.util.List;
import java.util.ArrayList;
import instituto.modelo.*;

public class Ventana extends JFrame {
	private DefaultTableModel mVacanteCurso;
	private JTable tablaPrincipal;
	private JScrollPane panelTabla;
	private JPanel panelBotones;

	public Ventana() {
		super("Instituto");
		cargarVentana();
		cargarTabla();
		cargarBotonAlta();
		cargarBotonInscripcion();
		cargarBotonBaja();
		cargarBotonDeudores();
		cargarPanelCentral();
		setVisible(true);
	}

	private void ventanaError(JFrame ventana, String mensaje, String nombre) {
		JOptionPane.showMessageDialog(ventana, mensaje, "Error : " + nombre, JOptionPane.ERROR_MESSAGE);
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
		mVacanteCurso = new DefaultTableModel(new String[] { "Curso", "Cupo", "Inscriptos", "Vacantes" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columna) {
				return columna > 0 ? Integer.class : String.class;
			}
		};
		try {
			List<List<Object>> listas = InscripcionDAO.cursoCupoInscriptosVacantes();
			for (List<Object> lista : listas) {
				mVacanteCurso.addRow(lista.toArray());
			}
		} catch (SQLException e) {
			ventanaError(this, "Error al obtener datos de curso", "vacantes curso");
		}
		tablaPrincipal = new JTable(mVacanteCurso);
		tablaPrincipal.setAutoCreateRowSorter(true);
		panelTabla = new JScrollPane(tablaPrincipal);
	}

	private void recargarTabla() {
		int cantidad = tablaPrincipal.getRowCount();
		for (int i = 0; i < cantidad; i++) {
			mVacanteCurso.removeRow(0);
		}
		try {
			List<List<Object>> lista = InscripcionDAO.cursoCupoInscriptosVacantes();
			for (List<Object> item : lista) {
				mVacanteCurso.addRow(item.toArray());
			}
		} catch (SQLException e) {
			ventanaError(this, "Error al obtener datos de curso", "vacantes curso");
		}
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
				ventanaError(formulario, "El alumno tiene que tener dni", "dni alumno");
				return;
			}
			if (nombre.isEmpty()) {
				ventanaError(formulario, "El alumno tiene que tener nombre", "nombre alumno");
				return;
			}
			if (apellido.isEmpty()) {
				ventanaError(formulario, "El alumno tiene que tener apellido", "apellido alumno");
				return;
			}
			try {
				AlumnoDAO.insertar(dni, nombre, apellido, correo);
				JOptionPane.showMessageDialog(formulario, "Alumno creado exitosamente!", "Exito",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException x) {
				switch (x.getErrorCode()) {
					case 1062:
						ventanaError(formulario, "El alumno con dni : " + dni + " ya existe.", "alumno");
						break;
					case 1406:
						String error = x.getMessage();
						if (error.contains("dni")) {
							ventanaError(formulario, "Error,el dni es muy largo. Ingrese 10 caracteres o menos",
									"longitud dni");
						} else if (error.contains("nombre")) {
							ventanaError(formulario, "Error,su nombre es muy largo. Ingrese 50 caracteres o menos",
									"longitud nombre");
						} else if (error.contains("apellido")) {
							ventanaError(formulario, "Error,su apellido es muy largo. Ingrese 50 caracteres o menos",
									"longitud apellido");
						} else if (error.contains("correo")) {
							ventanaError(formulario, "Error, el correo es muy largo. Ingrese 60 caracteres o menos",
									"longitud correo");
						}
						return;
					default:
						ventanaError(formulario, "Ocurrio un error en el sistema, intente nuevamente", "desconocido");
						break;
				}
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
		JComboBox<Alumno> alumnos = new JComboBox<Alumno>();
		JComboBox<Curso> cursos = new JComboBox<Curso>();
		try {
			List<Alumno> alumnosdb = AlumnoDAO.listar();
			List<Curso> cursodb = CursoDAO.listar();
			for (Alumno a : alumnosdb) {
				alumnos.addItem(a);
			}
			for (Curso c : cursodb) {
				cursos.addItem(c);
			}
		} catch (SQLException e) {
			ventanaError(ventana, "No se pudieron leer los datos, intente nuevamente", "desconocido");
		}
		JButton inscribir = new JButton("Inscribir");
		inscribir.addActionListener(e -> {
			Alumno alumno = (Alumno) alumnos.getSelectedItem();
			Curso curso = (Curso) cursos.getSelectedItem();
			try {
				InscripcionDAO.insertar(alumno, curso);
				JOptionPane.showMessageDialog(ventana, "Inscripcion exitosa", "Exito", JOptionPane.INFORMATION_MESSAGE);
				recargarTabla();
			} catch (SQLException x) {
				switch (x.getErrorCode()) {
					case 1062:
						ventanaError(ventana, "La inscripcion ya existia previamente.", "Inscripcion duplicada");
						break;
					case 1644:
						ventanaError(ventana, "El cupo de " + curso.getCodigo() + " esta lleno.", "Cupo lleno");
						break;
				}
			}
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
		botonBaja.addActionListener(e -> {
			JFrame ventana = formularioBaja();
		});
		panelBotones.add(botonBaja);
	}

	private JFrame formularioBaja() {
		JFrame ventana = new JFrame("Baja inscripcion");
		ventana.setSize(640, 360);
		ventana.setLocationRelativeTo(this);
		DefaultTableModel modelo = new DefaultTableModel(new String[] { "Alumno", "Curso" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		try {
			List<Inscripcion> lista = InscripcionDAO.listar();
			for (Inscripcion i : lista) {
				modelo.addRow(new Object[] { i.getAlumno(), i.getCurso() });
			}
		} catch (SQLException e) {
			ventanaError(ventana, "Ocurrio un error al cargar los datos", "carga de datos");
		}
		JTable tabla = new JTable(modelo);
		JScrollPane panelTablaBaja = new JScrollPane(tabla);
		JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton eliminar = new JButton("eliminar");
		JButton cancelar = new JButton("cancelar");
		cancelar.addActionListener(e -> ventana.dispose());
		eliminar.addActionListener(e -> {
			int seleccionado = tabla.getSelectedRow();
			if (seleccionado == -1) {
				JOptionPane.showMessageDialog(ventana, "Debe seleccionar una inscripcion",
						"Error : inscripcion no seleccionada", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int indexModel = tabla.convertRowIndexToModel(seleccionado);
			try {
				InscripcionDAO.borrar((Curso) modelo.getValueAt(indexModel, 1),
						(Alumno) modelo.getValueAt(indexModel, 0));
				JOptionPane.showMessageDialog(ventana, "Inscripcion borrada exitosamente", "Exito",
						JOptionPane.INFORMATION_MESSAGE);
				recargarTabla();
			} catch (SQLException x) {
				ventanaError(ventana, "Error al borrar inscripcion", "borrado inscripcion");
			}
			modelo.removeRow(indexModel);
		});
		panelBtn.add(eliminar);
		panelBtn.add(cancelar);
		ventana.setLayout(new BorderLayout(10, 10));
		ventana.add(panelTablaBaja, BorderLayout.CENTER);
		ventana.add(panelBtn, BorderLayout.SOUTH);

		ventana.setVisible(true);
		return ventana;
	}

	private void cargarBotonDeudores() {
		JButton boton = new JButton("Informe deudas");
		boton.addActionListener(e -> {
			JFrame ventana = ventanaDeudores();
		});
		panelBotones.add(boton);
	}

	private JFrame ventanaDeudores() {
		JFrame ventana = new JFrame("Baja inscripcion");
		ventana.setSize(640, 360);
		ventana.setLocationRelativeTo(this);
		DefaultTableModel modelo = new DefaultTableModel(new String[] { "Alumno", "Curso que debe", "Importe" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				return column == 2 ? Integer.class : String.class;
			}
		};
		try {
			List<List<Object>> deudores = InscripcionDAO.getDeudores();
			for (List<Object> deudor : deudores) {
				modelo.addRow(deudor.toArray());
			}
		} catch (SQLException e) {
			ventanaError(ventana, "Error cargando deudores", "deudores");
		}
		JTable tabla = new JTable(modelo);
		JScrollPane panelTablaDeudor = new JScrollPane(tabla);
		JButton volver = new JButton("Volver");
		volver.addActionListener(e -> ventana.dispose());
		JPanel panelBtnDeudor = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelBtnDeudor.add(volver);
		ventana.add(panelTablaDeudor, BorderLayout.CENTER);
		ventana.add(panelBtnDeudor, BorderLayout.SOUTH);
		ventana.setVisible(true);
		return ventana;
	}

}
