package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ConsultasAsignacion extends Conexion{
    
        private PreparedStatement ps = null;
        private ResultSet rs = null;
    
    public boolean registrar(long isbn, List<String> generos, ConsultasGenero cGen){
        Connection conn = getUpConnection();
        
        String sql = "INSERT INTO asignacion (n_isbn, n_genero) VALUES (?,?)";

        try {
            for (int i = 0; i < generos.size(); i++) {
                ps = conn.prepareStatement(sql);
                ps.setLong(1, isbn);
                ps.setInt(2, cGen.exportarGenero(generos.get(i)));
                ps.execute();
            }
            return true;
        } catch(SQLException e) {
            System.err.println(e);
            return false;
        }
    }
     
    public boolean eliminar(Asignacion asg){
        PreparedStatement ps = null;
        Connection conn = getUpConnection();
        
        String sql = "DELETE FROM asignacion WHERE n_isbn = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, asg.getN_isbn());
            ps.execute();
            return true;
        } catch(SQLException e) {
            System.err.println(e);
            return false;
        }
    }
      
    public boolean consultar(Asignacion asg){
        Connection conn = getUpConnection();
        
        String sql = "SELECT * FROM asignacion WHERE n_isbn = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, asg.getN_genero());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                asg.setN_isbn(rs.getInt("n_isbn"));
                asg.setN_genero(rs.getInt("n_genero"));
                return true;   
            }
            return false;
        } catch(SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
