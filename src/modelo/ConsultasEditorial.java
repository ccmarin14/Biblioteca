/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos-PC
 */
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
        }finally{
            try{
                conn.close();
            }catch (SQLException e){
                System.err.println(e);
            }
        }
    }
    
    public boolean modificar(Editorial edit){
    PreparedStatement ps  =  null;
    Connection conn = getUpConnection();
    String sql = "UPDATE editorial SET nombre = ?, pais = ? WHERE id_editorial =?" ;
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
    }finally{
        try{
            conn.close();
        }catch (SQLException e){
            System.err.println(e);
            }
        }
    }
    
    public boolean eliminar(Editorial edit){
    PreparedStatement ps  =  null;
    Connection conn = getUpConnection();
    String sql = "DELETE FROM producto WHERE id_editorial =?" ;
    try{
        ps = conn.prepareStatement(sql);
        ps.setInt(1, edit.getId_editorial());
        ps.execute();
        return true;
    }catch(SQLException e){
        System.err.println(e);
        return false;
    }finally{
        try{
            conn.close();
        }catch (SQLException e){
            System.err.println(e);
            }
        }
    }
    
        public boolean consultar(Editorial edit){
    PreparedStatement ps  =  null;
    ResultSet rs = null;
    Connection conn = getUpConnection();
    String sql = "SELECT * FROM editorial WHERE id_editorial =?" ;
    try{
        ps = conn.prepareStatement(sql);
        ps.setInt(1, edit.getId_editorial());
        rs = ps.executeQuery();
        if(rs.next()){
            edit.setId_editorial(rs.getInt("id_editorial"));
            edit.setNombre(rs.getString("nombre"));
            edit.setPais(rs.getString("pais"));
            return true;
        }
        
        
        return false;
    }catch(SQLException e){
        System.err.println(e);
        return false;
    }finally{
        try{
            conn.close();
        }catch (SQLException e){
            System.err.println(e);
            }
        }
    }
}
