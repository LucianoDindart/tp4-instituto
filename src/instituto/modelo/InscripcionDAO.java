package instituto.modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import instituto.modelo.Conexion;

public class InscripcionDAO {

    public static List<Inscripcion> listar() throws SQLException{
        String sql = "SELECT id_curso, id_alumno, fecha, estado_cuota FROM inscripcion";
        List<Inscripcion> inscripciones = new ArrayList<>();

        try (Connection con = Conexion.getConexion();
        PreparedStatement ps = con.prepareStatement(sql); 
        ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                int idCurso = rs.getInt("id_curso");
                int idAlumno = rs.getInt("id_alumno");
                boolean estadoCuota = "pagada".equalsIgnoreCase(rs.getString("estado_cuota"));

                Alumno alumno = AlumnoDAO.buscarPorId(idAlumno).orElseThrow(() -> new SQLException("No se encontro al alumno con el id " + idAlumno));
                Curso curso = CursoDAO.buscarPorId(idCurso).orElseThrow(() -> new SQLException("No se encontro un curso con el id " + idCurso));

                inscripciones.add(new Inscripcion(alumno, curso, estadoCuota));
            }
        }
        return inscripciones;
    }
    
    public static Optional<Inscripcion> buscarPorId(int idCurso, int idAlumno) throws SQLException{
        String sql = "SELECT id_curso, id_alumno, fecha, estado_cuota FROM inscripcion WHERE id_curso = ? AND id_alumno = ?";

        try (Connection con = Conexion.getConexion();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idCurso);
            ps.setInt(2, idAlumno);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    boolean estadoCuota = "pagada".equalsIgnoreCase(rs.getString("estado_cuota"));
                    Alumno alumno = AlumnoDAO.buscarPorId(idAlumno).orElseThrow(() -> new SQLException("No se encontro a un alumno con id " + idAlumno));
                    Curso curso = CursoDAO.buscarPorId(idCurso).orElseThrow(() -> new SQLException("No se encontro a un curso con el id " + idCurso));

                    return Optional.of(new Inscripcion(alumno, curso, estadoCuota));
                }
            }
        }
        return Optional.empty();
    }

    public static void insertar(int idCurso, int idAlumno, Inscripcion inscripcion) throws SQLException {
        String sql = "INSERT INTO inscripcion (id_curso, id_alumno, fecha, estado_cuota) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCurso);
            ps.setInt(2, idAlumno);
            ps.setDate(3, Date.valueOf(inscripcion.getFechaInscripcion()));
            ps.setString(4, inscripcion.isEstadoCuota() ? "pagada" : "sin pagar");

            ps.executeUpdate();
        }
    }

    public static boolean borrar(int idCurso, int idAlumno) throws SQLException{
        String sql = "DELETE FROM inscripcion WHERE id_curso = ? AND id_alumno = ?";

        try (Connection con = Conexion.getConexion();
        PreparedStatement ps =con.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            ps.setInt(2, idAlumno);
            return ps.executeUpdate() == 1;
        }
    }

    public static void modificar(int idCurso, int idAlumno, Inscripcion inscripcion) throws SQLException {
        String sql = "UPDATE inscripcion SET fecha = ?, estado_cuota = ? WHERE id_curso = ? AND id_alumno = ?";

        try (Connection con = Conexion.getConexion();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setDate(1, Date.valueOf(inscripcion.getFechaInscripcion()));
            ps.setString(2, inscripcion.isEstadoCuota() ? "pagada" : "sin pagar");
            ps.setInt(3, idCurso);
            ps.setInt(4, idAlumno);

            ps.executeUpdate();
        }
    }
}


