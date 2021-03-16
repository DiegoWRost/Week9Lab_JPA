/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import dataaccess.RoleDB;
import models.User;
import dataaccess.UserDB;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego Weidle Rost
 */
public class UserServices {
    
    RoleDB roleDB = new RoleDB();
    
    public User getUser(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.getUser(email);
        
        // return a note only if the owner of that note is the email address
        if (user.getEmail().equals(email)) {
            return user;
        } else {
            return null;
        }    
    }
    
    public boolean updateUser(String email, String firstName, String lastName, String password, String roleName, int active) {
        //TODO validation
        int role = roleDB.getRole(roleName);
        User user = new User(email, firstName, lastName, password, active, role, roleName);
        UserDB userDB = new UserDB();
        try {
            userDB.update(user);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean addUser(String email, String firstName, String lastName, String password, String roleName, int active){
        //TODO validation
        List<User> users = getAllUsers();
        UserDB userDB;
        User user;
        
        for (User userToCheck : users) {
            if (userToCheck.getEmail().equals(email)) {
                return false;
            } else {
                userDB = new UserDB();
                int role = roleDB.getRole(roleName);
                user = new User(email, firstName, lastName, password, active, role, roleName);
                try {
                    userDB.insert(user);
                    return true;
                } catch (Exception ex) {
                    Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    public boolean deleteUser(String email) {
        List<User> users = getAllUsers();
        UserDB userDB;
        
        for (User user : users) {
            System.out.println(user.getEmail()); //TESTING
            if (user.getEmail().equals(email)) {
                
                userDB = new UserDB();
                
                try {
                    userDB.delete(email);
                    return true;
                } catch (Exception ex) {
                    Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }    
        return false;
    }
    
    public List<User> getAllUsers(){
        UserDB userDB = new UserDB();
        try {
            return userDB.getAll();
        } catch (Exception ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
