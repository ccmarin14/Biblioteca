package modelo;

import java.sql.*;
import java.util.*;

public class ConsultasGenero extends Conexion{
    
    public boolean registrar(Genero gen){
        PreparedStatement ps = null;
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
        } /*finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }*/
    }
    
    public boolean modificar(Genero gen){
        PreparedStatement ps = null;
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
        } /*finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }*/
    }
    
    public boolean eliminar(Genero gen){
        PreparedStatement ps = null;
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
        } /*finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }*/
    }
      
    public boolean consultar(Genero gen){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = getUpConnection();
        
        String sql = "SELECT * FROM genero WHERE id_genero = ?";
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, gen.getId_genero());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                gen.setId_genero(rs.getInt("id_genero"));
                gen.setNombre(rs.getString("nombre"));
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

    public List consultar() {
        List<Genero> lstGen = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
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
