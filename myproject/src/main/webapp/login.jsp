<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Login - EliteWear</title>
<link rel="stylesheet" href="css/main.css">
</head>
<body>

<div class="account-page">
<div class="account-card">
<div class="form-tabs">
<button class="tab-btn active">Login</button>
</div>
<div class="form-content active">
<h2>Login</h2>
<form action="login" method="post">
<input type="text" name="username" placeholder="Username" required>
<input type="password" name="password" placeholder="Password" required>
<button type="submit" class="btn">Login</button>
</form>
<% if(request.getAttribute("loginError") != null){ %>
<p class="error-msg"><%= request.getAttribute("loginError") %></p>
<% } %>
<p style="margin-top:10px;">Don't have an account? <a href="register.jsp">Register here</a></p>
</div>
</div>
</div>

</body>
</html>