import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public List<Curso> listar() throws SQLException {
        String sql = "SELECT id_curso, codigo, idioma, nivel, turno, cupo, cuota FROM curso ORDER BY codigo";
        List<Curso> cursos = new ArrayList<>();

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) { 

            while (rs.next()) {
                cursos.add(new Curso(
                    rs.getInt("id_curso"),
                    rs.getString("codigo"),
                    rs.getString("idioma"),
                    rs.getString("nivel"),
                    rs.getString("turno"),
                    rs.getInt("cupo"),
                    rs.getDouble("cuota")
                ));
            }
        }
        return cursos;
    }

    public int insertar(String codigo, String idioma, String nivel, String turno, int cupo, double cuota) throws SQLException {
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

            try (ResultSet claves = ps.getGeneratedKeys()) { //[cite: 3]
                claves.next();
                return claves.getInt(1);
            }
        }
    }

    public boolean modificar(int idCurso, String codigo, String idioma, String nivel, String turno, int cupo, double cuota) throws SQLException {
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
    }

    public boolean borrar(int idCurso) throws SQLException {
        String sql = "DELETE FROM curso WHERE id_curso = ?";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) { 

            ps.setInt(1, idCurso);
            return ps.executeUpdate() == 1; 
        }
    }
}
    