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

    private static int obtenerIdCurso(String codigo)throws SQLException {
        String sql = "SELECT id_curso FROM curso WHERE codigo = ?";

        try(Connection con = Conexion.getConexion();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return rs.getInt("id_curso");
                }
            }
        }
        throw new SQLException("No se encontro el curso con el codigo: " + codigo);
    }

    private static int obtenerIdAlumno(String dni)throws SQLException {
        String sql = "SELECT id_alumno FROM alumno WHERE dni = ?";

        try(Connection con = Conexion.getConexion();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    return rs.getInt("id_alumno");
                }
            }
        }
        throw new SQLException("No se encontro el alumno con el dni: " + dni);
    }

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
        }catch(SQLException e){
            throw e;
        }
        return inscripciones;
    }
    
    public static Optional<Inscripcion> buscarPorId(Curso curso, Alumno alumno) throws SQLException{

        if(curso == null || alumno == null){
            return Optional.empty();
        }

        int idCurso = obtenerIdCurso(curso.getCodigo());
        int idAlumno = obtenerIdAlumno(alumno.getDni());

        String sql = "SELECT id_curso, id_alumno, fecha, estado_cuota FROM inscripcion WHERE id_curso = ? AND id_alumno = ?";

        try (Connection con = Conexion.getConexion();
        PreparedStatement ps = con.prepareStatement(sql)){
        

            ps.setInt(1, idCurso);
            ps.setInt(2, idAlumno);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    boolean estadoCuota = "pagada".equalsIgnoreCase(rs.getString("estado_cuota"));

                    return Optional.of(new Inscripcion(alumno, curso, estadoCuota));
                }
            }
        }catch(SQLException e){
            throw e;
        }
        return Optional.empty();
    }

    public static void insertar(Alumno alumno, Curso curso) throws SQLException {

        if(curso == null || alumno == null){
           throw new IllegalArgumentException("Debe poner un alumno y un curso valido");
        }

        Inscripcion inscripcion = new Inscripcion(alumno, curso, false);
        int idCurso = obtenerIdCurso(curso.getCodigo());
        int idAlumno = obtenerIdAlumno(alumno.getDni());

        String sql = "INSERT INTO inscripcion (id_curso, id_alumno, fecha, estado_cuota) VALUES (?, ?, ?, ?)";
        
        try (Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCurso);
            ps.setInt(2, idAlumno);
            ps.setDate(3, new Date(inscripcion.getFechaInscripcion().getTime()));
            ps.setString(4, inscripcion.isEstadoCuota() ? "pagada" : "sin pagar");

            ps.executeUpdate();
        }catch(SQLException e){
            throw e;
        }
    }

    public static boolean borrar(Curso curso, Alumno alumno) throws SQLException{

        if(curso == null || alumno == null){
           return false;
        }

        int idCurso = obtenerIdCurso(curso.getCodigo());
        int idAlumno = obtenerIdAlumno(alumno.getDni());

        String sql = "DELETE FROM inscripcion WHERE id_curso = ? AND id_alumno = ?";

        try (Connection con = Conexion.getConexion();
        PreparedStatement ps =con.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            ps.setInt(2, idAlumno);
            return ps.executeUpdate() == 1;
        }catch(SQLException e){
            throw e;
        }
    }

    public static void modificar(Inscripcion inscripcion) throws SQLException {

        if(inscripcion == null){
           throw new IllegalArgumentException("La inscripcion no puede ser nula");
        }

        int idCurso = obtenerIdCurso(inscripcion.getCurso().getCodigo());
        int idAlumno = obtenerIdAlumno(inscripcion.getAlumno().getDni());

        String sql = "UPDATE inscripcion SET fecha = ?, estado_cuota = ? WHERE id_curso = ? AND id_alumno = ?";

        try (Connection con = Conexion.getConexion();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setDate(1, new Date(inscripcion.getFechaInscripcion().getTime()));
            ps.setString(2, inscripcion.isEstadoCuota() ? "pagada" : "sin pagar");
            ps.setInt(3, idCurso);
            ps.setInt(4, idAlumno);

            ps.executeUpdate();
        }catch(SQLException e){
            throw e;
        }
    }

    public static List<List<Object>> cursoCupoInscriptosVacantes() throws SQLException{
        String sql = "SELECT curso, cupo, inscriptos, vacantes FROM vacante_curso";

        List<List<Object>> filas = new ArrayList<>();

        try (Connection con = Conexion.getConexion();
        PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                List<Object> fila = new ArrayList<>();
                fila.add(rs.getString("curso"));
                fila.add(rs.getInt("cupo"));
                fila.add(rs.getInt("inscriptos"));
                fila.add(rs.getInt("vacantes"));
                filas.add(fila);
            }
            return filas;
        }catch(SQLException e){
            throw e;
        }
    }
}


