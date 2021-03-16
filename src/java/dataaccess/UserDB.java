/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.User;

/**
 *
 * @author Diego Weidle Rost
 */
public class UserDB {
    
    private String querry = "";
    PreparedStatement statement = null;
    ResultSet result = null;
    RoleDB roleDB = new RoleDB();
    
    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        
        querry = "SELECT * FROM user";
        
        try {
            statement = connection.prepareStatement(querry);
            result = statement.executeQuery();
            while (result.next()) {
                String email = result.getString(1);
                int active = result.getInt(2);
                String firstName = result.getString(3);
                String lastName = result.getString(4);
                String password = result.getString(5);
                int role = result.getInt(6);
                String roleName = roleDB.getRole(role);
                
                User user = new User(email, firstName, lastName, password, active, role, roleName);
                users.add(user);
            }
            return users;
        } finally {
            DBUtil.closeResultSet(result);
            DBUtil.closePreparedStatement(statement);
            pool.freeConnection(connection);
        }
    }
    
    public User getUser(String email) throws Exception {
        User user = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        querry = "SELECT * FROM user WHERE email=?";
        
        try {
            statement = connection.prepareStatement(querry);
            statement.setString(1, email);
            result = statement.executeQuery();
            if (result.next()) {
                int active = result.getInt(2);
                String firstName = result.getString(3);
                String lastName = result.getString(4);
                String password = result.getString(5);
                int role = result.getInt(6);
                String roleName = roleDB.getRole(role);
                user = new User(email, firstName, lastName, password, active, role, roleName);
            }
        } finally {
            DBUtil.closeResultSet(result);
            DBUtil.closePreparedStatement(statement);
            pool.freeConnection(connection);
        }
        return user;
    }

    public void insert(User user) throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        querry = "INSERT INTO user (email, active, first_name, last_name, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            statement = connection.prepareStatement(querry);
            
            statement.setString(1, user.getEmail());
            statement.setInt(2, user.isActive());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getPassword());
            statement.setInt(6, user.getRole());
            
            statement.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(statement);
            pool.freeConnection(connection);
        }
    }

    public void update(User user) throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        querry = "UPDATE user SET first_name=?, last_name=?, password=? WHERE email=?";
        
        try {
            statement = connection.prepareStatement(querry);
            
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            
            statement.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(statement);
            pool.freeConnection(connection);
        }
    }

    public void delete(String email) throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        querry = "DELETE FROM user WHERE email=?";
        
        try {
            statement = connection.prepareStatement(querry);
            statement.setString(1, email);
            statement.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(statement);
            pool.freeConnection(connection);
        }
    }
}
