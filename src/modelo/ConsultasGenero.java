package modelo;

import java.sql.*;
import java.util.*;

public class ConsultasGenero extends Conexion{
    
    //Variables
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public boolean registrar(Genero gen){
        Connection conn = getUpConnection();
        
        String sql = "INSERT INTO genero (id_genero, nombre) VALUES (?,?)";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, gen.getId_genero());
            ps.setString(2, gen.getNombre());
            ps.execute();
            return true;
        } catch(SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean modificar(Genero gen){
        Connection conn = getUpConnection();
        
        String sql = "UPDATE genero SET nombre = ? WHERE id_genero = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, gen.getNombre());
            ps.setInt(2, gen.getId_genero());
            ps.execute();
            return true;
        } catch(SQLException e) {
            System.err.println(e);
            return false;
        }
    }
    
    public boolean eliminar(Genero gen){
        Connection conn = getUpConnection();
        
        String sql = "DELETE FROM genero WHERE id_genero = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, gen.getId_genero());
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
    
    public int exportarGenero (String genero) {
        int idGenero = 0;
        Connection conn = getUpConnection();
        
         String sql = "SELECT id_genero FROM genero WHERE nombre = ?";
         
         try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, genero);
            rs = ps.executeQuery();
            rs.next();
            idGenero = rs.getInt(1);
        } catch (Exception e) {
            System.err.println(e);
        }
        return idGenero;
    }
}
