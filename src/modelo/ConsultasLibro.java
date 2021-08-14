package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasLibro extends Conexion{
    
    public boolean registrar(Libro ejemplar){
        PreparedStatement ps = null;
        Connection conn = getUpConnection();
        
        String sql = "INSERT INTO genero (isbn, nombre, cantidad, autor, calificacion, n_editorial) VALUES (?,?,?,?,?,?)";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ejemplar.getIsbn());
            ps.setString(2, ejemplar.getNombre());
            ps.setInt(3, ejemplar.getCantidad());
            ps.setString(4, ejemplar.getAutor());
            ps.setFloat(5, ejemplar.getCalificacion());
            ps.setInt(6, ejemplar.getN_editorial());
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
    
    public boolean modificar(Libro ejemplar){
        PreparedStatement ps = null;
        Connection conn = getUpConnection();
        
        String sql = "UPDATE libro SET nombre = ?, cantidad = ?, autor = ?, calificacion = ?, n_editorial = ? WHERE isbn = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ejemplar.getIsbn());
            ps.setString(2, ejemplar.getNombre());
            ps.setInt(3, ejemplar.getCantidad());
            ps.setString(4, ejemplar.getAutor());
            ps.setFloat(5, ejemplar.getCalificacion());
            ps.setInt(6, ejemplar.getN_editorial());
            ps.setInt(7, ejemplar.getIsbn());
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
    
    public boolean eliminar(Libro ejemplar){
        PreparedStatement ps = null;
        Connection conn = getUpConnection();
        
        String sql = "DELETE FROM libro WHERE isbn = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ejemplar.getIsbn());
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
      
    public boolean consultar(Libro ejemplar){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = getUpConnection();
        
        String sql = "SELECT * FROM genero WHERE isbn = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ejemplar.getIsbn());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                ejemplar.setNombre(rs.getString("nombre"));
                ejemplar.setCantidad(rs.getInt("cantidad"));
                ejemplar.setAutor(rs.getString("autor"));
                ejemplar.setCalificacion(rs.getFloat("calififacion"));
                ejemplar.setN_editorial(rs.getInt("n_editorial"));
                return true;   
            }
            return false;
        } catch(SQLException e) {
            System.err.println(e);
            return false;
        } /*finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasGenero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
    }
}
