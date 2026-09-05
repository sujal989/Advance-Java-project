package virinchi.servlet;

import virinchi.controller.UserController;
import virinchi.controller.UserControllerImplement;
import virinchi.model.UserTable;
import virinchi.util.DbConnection;
import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserController uc = new UserControllerImplement();
        UserTable user = uc.userLogin(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);
            
            // Check if user is admin
            String userRole = getUserRole(username);
            System.out.println("User role for " + username + " is: " + userRole);
            
            if ("admin".equals(userRole)) {
                // Redirect to admin panel
                System.out.println("Redirecting to admin panel");
                response.sendRedirect("admin");
            } else {
                // Redirect to regular user page
                System.out.println("Redirecting to index.jsp");
                response.sendRedirect("index.jsp");
            }
        } else {
            request.setAttribute("loginError", "Invalid username or password!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }
    
    // Get user role from database
    private String getUserRole(String username) {
        try (Connection con = DbConnection.getConnection()) {
            String query = "SELECT role FROM users WHERE username = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String role = rs.getString("role");
                        System.out.println("Retrieved role from DB: " + role);
                        return role;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "user";
    }
}