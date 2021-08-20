package modelo;

import java.sql.*;
import java.util.*;

public class ConsultasEditorial extends Conexion{
    
    public boolean registrar(Editorial edit){
        PreparedStatement ps  =  null;
        Connection conn = getUpConnection();
        
        String sql = "INSERT INTO editorial (id_editorial,nombre,pais) VALUES(?,?,?)";
        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, edit.getId_editorial());
            ps.setString(2, edit.getNombre());
            ps.setString(3, edit.getPais());
            ps.execute();
            return true;
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }
    }
    
    public boolean modificar(Editorial edit){
        PreparedStatement ps  =  null;
        Connection conn = getUpConnection();

        String sql = "UPDATE editorial SET nombre = ?, pais = ? WHERE id_editorial =?";

        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, edit.getNombre());
            ps.setString(2, edit.getPais());
            ps.setInt(3, edit.getId_editorial());
            ps.execute();
            return true;
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }
    }
    
    public boolean eliminar(Editorial edit){
        PreparedStatement ps  =  null;
        Connection conn = getUpConnection();

        String sql = "DELETE FROM editorial WHERE id_editorial =?" ;

        try{
            ps = conn.prepareStatement(sql);
            ps.setInt(1, edit.getId_editorial());
            ps.execute();
            return true;
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }
    }
    
    
    public List consultar() {
        List<Editorial> lstEdit = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = getUpConnection();
        
        String sql = "SELECT * FROM editorial";
        
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Editorial g = new Editorial();
                g.setId_editorial(rs.getInt(1));
                g.setNombre(rs.getString(2));
                g.setPais(rs.getString(3));
                lstEdit.add(g);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return lstEdit;
    }
    
    public Queue nombresEditorial() {
        Queue<String> lstEditorial = new LinkedList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = getUpConnection();
        
        String sql = "SELECT nombre FROM editorial";
        
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lstEditorial.add(rs.getString(1));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return lstEditorial;
    }
}
