package instituto.modelo;
import java.lang.classfile.ClassFile.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Statement;

public class CursoDAO {

    public static List<Curso> listar() throws SQLException {
        String sql = "SELECT id_curso, codigo, idioma, nivel, turno, cupo, cuota FROM curso ORDER BY codigo";
        List<Curso> cursos = new ArrayList<>();

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) { 

            while (rs.next()) {
                cursos.add(new Curso(
                    rs.getString("codigo"),
                    rs.getString("idioma"),
                    rs.getString("nivel"),
                    rs.getString("turno"),
                    rs.getInt("cupo"),
                    rs.getDouble("cuota")
                ));
            }
        
        }
        catch (SQLException e) {
            throw e;
        
        }
        
        return cursos;
        
    }

    public static int insertar(String codigo, String idioma, String nivel, String turno, int cupo, double cuota) throws SQLException {
        String sql = "INSERT INTO curso (codigo, idioma, nivel, turno, cupo, cuota) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, codigo);
            ps.setString(2, idioma);
            ps.setString(3, nivel);
            ps.setString(4, turno);
            ps.setInt(5, cupo);
            ps.setDouble(6, cuota);
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

    public static boolean modificar(int idCurso, String codigo, String idioma, String nivel, String turno, int cupo, double cuota) throws SQLException {
        String sql = "UPDATE curso SET codigo = ?, idioma = ?, nivel = ?, turno = ?, cupo = ?, cuota = ? WHERE id_curso = ?";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) { 

            ps.setString(1, codigo);
            ps.setString(2, idioma);
            ps.setString(3, nivel);
            ps.setString(4, turno);
            ps.setInt(5, cupo);
            ps.setDouble(6, cuota);
            ps.setInt(7, idCurso);

            return ps.executeUpdate() == 1;
        }
        catch (SQLException e) {
            throw e;
        
        }
    }

    public static boolean borrar(int idCurso) throws SQLException {
        String sql = "DELETE FROM curso WHERE id_curso = ?";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) { 

            ps.setInt(1, idCurso);
            return ps.executeUpdate() == 1; 
        }
        catch (SQLException e) {
            throw e;
        
        }


    }
    
    
    public static Optional<Curso> buscarPorId(int id) throws SQLException {
        String sql = "SELECT codigo, idioma, nivel, turno, cupo, cuota FROM curso WHERE id = ?";

        try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Curso curso = new Curso(
                        rs.getString("codigo"),
                        rs.getString("idioma"),
                        rs.getString("nivel"),
                        rs.getString("turno"),
                        rs.getInt("cupo"),
                        rs.getDouble("cuota")
                    );
                    return Optional.of(curso);

                }
            }


        }catch (SQLException e) {
            throw e; 
        }
        return Optional.empty(); 








    }

    
    
       

}



    