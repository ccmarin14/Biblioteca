package modelo;

import java.sql.*;
import java.util.*;

public class ConsultasLibro extends Conexion{
    
    //Variables
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public boolean registrar(Libro ejemplar){
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
        }
    }
    
    public boolean modificar(Libro ejemplar){
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
        }
    }
    
    public boolean eliminar(Libro ejemplar){
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
        }
    }
      
    public List consultar() {
        List<Genero> lstGen = new ArrayList<>();
        Connection conn = getUpConnection();
        
        String sql = "SELECT * FROM genero";
        
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Genero g = new Genero();
                g.setId_genero(rs.getInt(1));
                g.setNombre(rs.getString(2));
                lstGen.add(g);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return lstGen;
    }
}
