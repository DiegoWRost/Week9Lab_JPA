<%-- 
    Document   : users
    Created on : Mar 10, 2021, 9:01:04 AM
    Author     : 468181
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Week 8 Lab</title>
        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
        <link rel="stylesheet" href="users.css" />
    </head>

    <body>
        <h1>Manage Users</h1>

        <table>
            <tr>
                <th>Email</th>
                <th>First Name</th>
                <th>Last name</th>
                <th>Role </th>
                <th>Edit </th>
                <th>Delete</th>
            </tr>
            <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.email}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.role.roleName}</td>
                <td>
                    <form method="POST" action="user">
                        <input type="hidden" name="action" value="edit">
                        <input type="submit" value="edit">
                        <input type="hidden" name="email" value="${user.email}">
                    </form>
                </td>
                <td>
                    <form method="POST" action="user">
                        <input type="hidden" name=action value="delete">
                        <input type="submit" value="delete">
                        <input type="hidden" name="email" value="${user.email}">
                    </form>
                </td>
            </tr>
            </c:forEach>
        </table>
        
        <c:if test="${selectedUser == null}"> 
            <h1>Add Users</h1>
            <form action="user" method="POST">
                <table>
                    <tr>
                        <td><label for="email">E-mail</label></td>
                        <td><input type="email" name="email"></td>
                    </tr>
                    <tr>
                        <td><label for="fname">First Name</label></td>
                        <td><input type="text" name="fname"></td>
                    </tr>
                    <tr>
                        <td><label for="lname">Last Name</label></td>
                        <td><input type="text" name="lname"></td>
                    </tr>
                    <tr>
                        <td><label for="password">Password</label></td>
                        <td><input type="password" name="password"></td>
                    </tr>
                    <tr>
                        <td><label for="domain">Role</label></td>
                        <td><select name="domain">
                            <c:forEach var="role" items="${roles}">
                                <option value="${role.roleName}">${role.roleName}</option>
                            </c:forEach>
                        </select>

                        <input type="submit" value="add" class="save">
                        <input type="hidden" value="add" name="action">
                        </td>
                    </tr>
                </table>
            </form>
        </c:if>

        <c:if test="${selectedUser != null}">
            <h1>Edit Users</h1>

            <table>
                <tr><td>
                    <form action="user" method="post">
                    <input type="hidden" name="email" value=${selectedUser.email}>
                    <input type="text" name="fname" value=${selectedUser.firstName}>
                </td></tr>
                <tr><td>
                        <input type="text" name="lname" value=${selectedUser.lastName}>  
                </td></tr>
                <tr><td>
                        <input type="password" name="password" value=${selectedUser.password}>
                </td></tr>
                <tr>
                    <td>
                        <input type="submit" value="save">
                        <input type="hidden" name="action" value="save">
                        </form>
                        <form class="formCancel" action="user" method="post">
                            <input type="submit" value="cancel">
                            <input type="hidden" name="action" value="cancel">
                        </form>
                    </td>
                </tr>
            </table>
        </c:if>
  
        <br> ${message}

    </body>
</html>
