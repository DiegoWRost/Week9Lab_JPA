package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.UserServices;
import services.RoleServices;
import models.Role;
import models.User;

/**
 *
 * @author 829364
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        displayMainPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserServices us = new UserServices();

        String email = request.getParameter("email");
        String firstName = request.getParameter("fname");
        String lastName = request.getParameter("lname");
        String password = request.getParameter("password");
        String role = request.getParameter("domain");
        int active = 0;
        if (request.getParameter("active") != null && request.getParameter("active").equals("on")) {
            active = 1;
        };

        String action = request.getParameter("action");

        User selectedUser = null;

        String message = null;

        try {
            switch (action) {
                case "edit":
                    selectedUser = us.getUser(email);
                    break;
                case "add":
                    if (us.addUser(email, firstName, lastName, password, role, active)) {
                        message = "User added";
                    } else {
                        message = "Could not add new user";
                    }
                    break;
                case "save":
                    if (us.updateUser(email, firstName, lastName, password, role, active)) {
                        message = "User updated";
                    } else {
                        message = "Could not update user";
                    }
                    break;
                case "delete":
                    if (us.deleteUser(email)) {
                        message = "User deleted";
                    } else {
                        message = "Could not delete user";
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute("message", "error");
        }
        
        request.setAttribute("selectedUser", selectedUser);
        request.setAttribute("message", message);

        displayMainPage(request, response);
    }

    protected void displayMainPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserServices us = new UserServices();
        List<User> users = us.getAllUsers();

        RoleServices rs = new RoleServices();
        List<String> roles = rs.getAllRoles();

        request.setAttribute("users", users);
        request.setAttribute("roles", roles);

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }
}
