package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasAsignacion extends Conexion{
    
    public boolean registrar(Asignacion asg){
        PreparedStatement ps = null;
        Connection conn = getUpConnection();
        
        String sql = "INSERT INTO asignacion (n_isbn, n_genero) VALUES (?,?)";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, asg.getN_isbn());
            ps.setInt(2, asg.getN_genero());
            ps.execute();
            return true;
        } catch(SQLException e) {
            System.err.println(e);
            return false;
        } /*finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }*/
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
        } /*finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }*/
    }
      
    public boolean consultar(Asignacion asg){
        PreparedStatement ps = null;
        ResultSet rs = null;
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
