<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Register - EliteWear</title>
<link rel="stylesheet" href="css/main.css">
</head>
<body>

<div class="account-page">
  <div class="account-card">
    <div class="form-tabs">
      <button class="tab-btn active">Register</button>
    </div>
    <div class="form-content active">
      <h2>Register</h2>
      <form action="signup" method="post">
        <input type="text" name="username" placeholder="Username" required>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit" class="btn">Register</button>
      </form>
      <% if(request.getAttribute("signupError") != null) { %>
        <p class="error-msg"><%= request.getAttribute("signupError") %></p>
      <% } %>
      <p style="margin-top:10px;">Already have an account? <a href="login.jsp">Login here</a></p>
    </div>
  </div>
</div>

</body>
</html>
	