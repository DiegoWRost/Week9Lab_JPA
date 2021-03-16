/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Role;

/**
 *
 * @author 468181
 */
public class RoleDB {
    private String querry = "";
    PreparedStatement statement = null;
    ResultSet result = null;
    
    public List<Role> getAll() throws Exception
    {
        List<Role> roles = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        querry = "SELECT * FROM role";
        
        try {
            statement = connection.prepareStatement(querry);
            result = statement.executeQuery();
            while (result.next()) {
                int roleId = result.getInt(1);
                String userRole = result.getString(2);
                Role allRoles = new Role(roleId, userRole);
                roles.add(allRoles);
            }
        } finally {
            DBUtil.closeResultSet(result);
            DBUtil.closePreparedStatement(statement);
            pool.freeConnection(connection);
        }
        return roles;
    }
    
    public int getRole (String roleName) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        
        querry = "SELECT role_id FROM role WHERE role_name=?";
        
        try {
            statement = connection.prepareStatement(querry);
            statement.setString(1, roleName);
            result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closeResultSet(result);
            DBUtil.closePreparedStatement(statement);
            pool.freeConnection(connection);
        }
        return 0;
    }
    
    public String getRole (int role) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        
        querry = "SELECT role_name FROM role WHERE role_id=?";
        
        try {
            statement = connection.prepareStatement(querry);
            statement.setInt(1, role);
            result = statement.executeQuery();
            result.next();
 
            return result.getString(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(RoleDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DBUtil.closeResultSet(result);
            DBUtil.closePreparedStatement(statement);
            pool.freeConnection(connection);
        }
        return null;
    }
}
