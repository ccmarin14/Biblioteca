package modelo;

import java.sql.*;
import java.util.*;

public class ConsultasLibro extends Conexion{
    
    //Variables
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public boolean registrar(Libro ejemplar){
        Connection conn = getUpConnection();
        
        String sql = "INSERT INTO libro (isbn, nombre, cantidad, autor, calificacion, n_editorial) VALUES (?,?,?,?,?,?)";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setLong(1, ejemplar.getIsbn());
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
            ps.setLong(1, ejemplar.getIsbn());
            ps.setString(2, ejemplar.getNombre());
            ps.setInt(3, ejemplar.getCantidad());
            ps.setString(4, ejemplar.getAutor());
            ps.setFloat(5, ejemplar.getCalificacion());
            ps.setInt(6, ejemplar.getN_editorial());
            ps.setLong(7, ejemplar.getIsbn());
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
            ps.setLong(1, ejemplar.getIsbn());
            ps.execute();
            return true;
        } catch(SQLException e) {
            System.err.println(e);
            return false;
        }
    }
      
    public List consultar() {
        List<Libro> lstLibro = new ArrayList<>();
        Connection conn = getUpConnection();
        
        String sql = "SELECT * FROM libro";
        
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Libro ejp = new Libro();
                ejp.setIsbn(rs.getLong(1));
                ejp.setNombre(rs.getString(2));
                ejp.setCantidad(rs.getInt(3));
                //Reemplazar para que importe el nombre
                ejp.setAutor(rs.getString(4));
                ejp.setCalificacion(rs.getFloat(5));
                ejp.setN_editorial(rs.getInt(6));
                lstLibro.add(ejp);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return lstLibro;
    }
    
    /*
    public String importarEditorial (int nEdit) {
        String editorial = null;
        Connection conn = getUpConnection();
        
        String sql = "SELECT nombre FROM editorial WHERE id_editorial= ?";
        
         try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,nEdit);
            rs = ps.executeQuery();
            editorial = rs.getString("nombre");
            return editorial;
        } catch (Exception e) {
            System.err.println(e);
            return editorial;
        }
    }*/
}
