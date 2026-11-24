package instituto.modelo; 
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
public class AlumnoDAO {
    private static Map<String,Integer> mapaDniId = new HashMap<>();

    


    public static List<Alumno> listar() throws SQLException {
        String sql = "SELECT id_alumno, dni, nombre, apellido, correo FROM alumno ORDER BY apellido, nombre";
        List<Alumno> alumnos = new ArrayList<>();

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                mapaDniId.put(rs.getString("dni"),rs.getInt("id_alumno"));
                alumnos.add(new Alumno(
                    
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo")
                )); 
            }

            
        }
        catch (SQLException e) {
            throw e;
        
        }
        return alumnos;
    }

    public static int insertar(String dni, String nombre, String apellido, String correo) throws SQLException {
        String sql = "INSERT INTO alumno (dni, nombre, apellido, correo) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, correo);
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                claves.next();
                return claves.getInt(1);
            }
        }
        catch (SQLException e) {
            throw e;
        
        }
    }

    public static int modificar(int idAlumno, String dni, String nombre, String apellido) throws SQLException {
        String sql = "UPDATE alumno SET dni = ?, nombre = ?, apellido = ?, correo = ? WHERE id_alumno = ?";

        try (Connection con = Conexion.getConexion();

             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setInt(4, idAlumno);

            int resultado = ps.executeUpdate();
            if (resultado == 1) {
                mapaDniId.put(dni, idAlumno);
            
            }
            return resultado;     
        } catch (SQLException e) {
            throw e;
        
        }
        

        

        
    }

    public static int borrar(String dni) throws SQLException {
        Integer idAlumno = getidAlumno(dni);
        if (idAlumno == null) {
            throw new SQLException("el alumno no existe. ");
        }
        String sql = "DELETE FROM alumno WHERE id_alumno = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
             

            ps.setInt(1, idAlumno);
            int resultado = ps.executeUpdate();
            if (resultado == 1) {
                mapaDniId.remove(dni);
                
            }
            return resultado; 

        }
        catch (SQLException e) {
            throw e;
        
        }

    
    }
    public static Integer getidAlumno (String dniAlumno) {
          return mapaDniId.get(dniAlumno);
        

    }

    public static Optional<Alumno> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id_alumno, dni, nombre, apellido, correo FROM alumno WHERE id_alumno = ?";
        
        
        try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    Alumno alumno = new Alumno(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo    ")
                    );
                    return Optional.of(alumno);

                }
            } catch (SQLException e) {
                throw e;
            }
        }

         return Optional.empty();

    }

    
            
        



         


}
    
 