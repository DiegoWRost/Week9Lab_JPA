/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Role;

/**
 *
 * @author 468181
 */
public class RoleDB {
    
    public List<Role> getAll() throws Exception
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Query query = em.createQuery("SELECT r FROM Role r");
            return (ArrayList<Role>) query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public int getRole (String roleName) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Role role  = em.find(Role.class, roleName);
            return role.getRoleId();
        } finally {
            em.close();
        }
    }
    
    public String getRole (int roleID) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            Role role  = em.find(Role.class, roleID);
            return role.getRoleName();
        } finally {
            em.close();
        }
    }
}
