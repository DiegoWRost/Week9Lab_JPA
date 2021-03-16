/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import java.util.ArrayList;
import models.Role;
import dataaccess.RoleDB;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 468181
 */
public class RoleServices {

    public RoleServices() {

    }

    public List<String> getAllRoles() {

        RoleDB roleDB = new RoleDB();
        try {
            List<Role> allRoles = roleDB.getAll();
            ArrayList<String> roles = new ArrayList<String>();
            for (Role r : allRoles) {
                roles.add(r.getRole());
            }
        } catch (Exception ex) {
            Logger.getLogger(RoleServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
