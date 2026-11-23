import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Map;
import java.util.HashMap;
public class AlumnoDAO {
    private static Map<String,Integer> mapaCodigoId = new HashMap<>();

    


    public static List<Alumno> listar() throws SQLException {
        String sql = "SELECT id_alumno, dni, nombre, apellido FROM alumno ORDER BY apellido, nombre";
        List<Alumno> alumnos = new ArrayList<>();

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                alumnos.add(new Alumno(
                    
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo")
                )); 
            }
        }
        return alumnos;
    }

    public static int insertar(String dni, String nombre, String apellido) throws SQLException {
        String sql = "INSERT INTO alumno (dni, nombre, apellido) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                claves.next();
                return claves.getInt(1);
            }
        }
    }

    public static boolean modificar(int idAlumno, String dni, String nombre, String apellido) throws SQLException {
        String sql = "UPDATE alumno SET dni = ?, nombre = ?, apellido = ? WHERE id_alumno = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setInt(4, idAlumno);

            return ps.executeUpdate() == 1;
        }
    }

    public static boolean borrar(int idAlumno) throws SQLException {
        String sql = "DELETE FROM alumno WHERE id_alumno = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAlumno);
            return ps.executeUpdate() == 1;
        }

    
    }
    public static Integer getidAlumno (String dniAlumno) {
          return mapaCodigoId.get(dniAlumno);
        

    }


}
    
 