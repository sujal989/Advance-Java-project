<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="virinchi.model.UserTable" %>
<%@ page session="true" %>
<%
    UserTable user = (UserTable) session.getAttribute("loggedUser");
    // Don't redirect, just show info if not logged in
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account - EliteWear</title>
    <link rel="stylesheet" href="css/main.css">
</head>
<body>

<div class="account-page">
    <div class="account-card">
        <% if(user != null){ %>
            <h2>Welcome, <%= user.getUsername() %>!</h2>
            <p><strong>Email:</strong> <%= user.getEmail() %></p>

            <form action="logout" method="get">
                <button type="submit" class="btn logout-btn">Logout</button>
            </form>

            <p style="margin-top:15px;">
                <a href="index.jsp">Go to Home</a> | 
                <a href="product.jsp">Browse Products</a>
            </p>
        <% } else { %>
            <h2>You are not logged in!</h2>
            <p><a href="login.jsp">Login here</a></p>
        <% } %>
    </div>
</div>

</body>
</html>
