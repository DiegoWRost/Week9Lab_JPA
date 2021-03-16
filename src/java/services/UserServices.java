/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import dataaccess.RoleDB;
import models.User;
import dataaccess.UserDB;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Role;

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
    
    public boolean updateUser(String email, String firstName, String lastName, String password) {
        boolean isNull = false;
        String validation = validateUser(email, firstName, lastName, password, "actualRole", "updateUser");
        if (!validation.isEmpty()) {
            System.out.println(validation);
            isNull = true;
        }
        
        if (!isNull) {
            try {
                UserDB userDB = new UserDB();
                User user = userDB.getUser(email);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPassword(password);
                userDB.update(user);
                return true;
            } catch (Exception ex) {
                Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Update failed");
        return false;
    }
    
    public boolean addUser(String email, String firstName, String lastName, String password, String roleName, boolean active){
        boolean isNull = false;
        String validation = validateUser(email, firstName, lastName, password, roleName, "addUser");
        if (!validation.isEmpty()) {
            System.out.println(validation);
            isNull = true;
        }
        
        if (!isNull) {
            Collection<User> users = getAllUsers();
            UserDB userDB;
            User user;

            for (User userToCheck : users) {
                if (userToCheck.getEmail().equals(email)) {
                    return false;
                } else {
                    userDB = new UserDB();
                    int roleId = roleDB.getRole(roleName);
                    Role role = roleDB.getRoleObject(roleId);
                    user = new User(email, active, firstName, lastName, password);
                    user.setActive(active);
                    user.setRole(role);
                    try {
                        userDB.insert(user);
                        return true;
                    } catch (Exception ex) {
                        Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        System.out.println("Add failed");
        return false;
    }
    
    public boolean deleteUser(String email) {
        Collection<User> users = getAllUsers();
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
    
    public Collection<User> getAllUsers(){
        UserDB userDB = new UserDB();
        try {
            return userDB.getAll();
        } catch (Exception ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String validateUser (String email, String firstName, String lastName, String password, String roleName, String method) {
        String message = "UserServices:" + method + " NULL OR EMPTY ARGUMENT: ";
        if (email == null || email.isEmpty()) {
            message += "email";
        } else if (firstName == null || firstName.isEmpty()) {
            message += "firstName";
        } else if (lastName == null || lastName.isEmpty()) {
            message += "lastName";
        } else if (password == null || password.isEmpty()) {
            message += "password";
        } else if (roleName == null || roleName.isEmpty()) {
            message += "roleName";
        } else {
            message = "";
        }
        return message;
    }
}
