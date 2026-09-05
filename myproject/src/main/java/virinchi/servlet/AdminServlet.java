
	package virinchi.servlet;

	import virinchi.util.DbConnection;
	import virinchi.model.UserTable;
	import java.io.IOException;
	import java.sql.*;
	import javax.servlet.*;
	import javax.servlet.http.*;
	import javax.servlet.annotation.WebServlet;

	@WebServlet("/admin")
	public class AdminServlet extends HttpServlet {

	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        // Check if user is logged in
	        HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("loggedUser") == null) {
	            response.sendRedirect("login.jsp");
	            return;
	        }

	        UserTable user = (UserTable) session.getAttribute("loggedUser");
	        
	        // Check if user is admin
	        String userRole = getUserRole(user.getUsername());
	        if (!"admin".equals(userRole)) {
	            response.sendRedirect("index.jsp?error=unauthorized");
	            return;
	        }

	        // Get statistics
	        int totalUsers = countTotalUsers();
	        int activeUsers = countActiveUsers();
	        int totalProducts = countProducts();
	        
	        request.setAttribute("totalUsers", totalUsers);
	        request.setAttribute("activeUsers", activeUsers);
	        request.setAttribute("totalProducts", totalProducts);

	        // Forward to admin panel page
	        RequestDispatcher rd = request.getRequestDispatcher("adminpanel.jsp");
	        rd.forward(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String action = request.getParameter("action");

	        if ("deleteUser".equals(action)) {
	            int userId = Integer.parseInt(request.getParameter("userId"));
	            deleteUser(userId);
	            response.sendRedirect("admin");

	        } else if ("toggleStatus".equals(action)) {
	            int userId = Integer.parseInt(request.getParameter("userId"));
	            toggleUserStatus(userId);
	            response.sendRedirect("admin");

	        } else if ("makeAdmin".equals(action)) {
	            int userId = Integer.parseInt(request.getParameter("userId"));
	            changeUserRole(userId, "admin");
	            response.sendRedirect("admin");

	        } else if ("removeAdmin".equals(action)) {
	            int userId = Integer.parseInt(request.getParameter("userId"));
	            changeUserRole(userId, "user");
	            response.sendRedirect("admin");
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
	                        return rs.getString("role");
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "user";
	    }

	    // Count total users
	    private int countTotalUsers() {
	        try (Connection con = DbConnection.getConnection()) {
	            String query = "SELECT COUNT(*) FROM users";
	            try (PreparedStatement ps = con.prepareStatement(query);
	                 ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt(1);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }

	    // Count active users
	    private int countActiveUsers() {
	        try (Connection con = DbConnection.getConnection()) {
	            String query = "SELECT COUNT(*) FROM users WHERE is_active = TRUE";
	            try (PreparedStatement ps = con.prepareStatement(query);
	                 ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt(1);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }

	    // Count products
	    private int countProducts() {
	        try (Connection con = DbConnection.getConnection()) {
	            String query = "SELECT COUNT(*) FROM products WHERE is_active = TRUE";
	            try (PreparedStatement ps = con.prepareStatement(query);
	                 ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt(1);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return 0;
	    }

	    // Delete user from database
	    private void deleteUser(int userId) {
	        try (Connection con = DbConnection.getConnection()) {
	            String query = "DELETE FROM users WHERE id = ?";
	            try (PreparedStatement ps = con.prepareStatement(query)) {
	                ps.setInt(1, userId);
	                ps.executeUpdate();
	                System.out.println("✅ User deleted: ID " + userId);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Toggle user active/inactive status
	    private void toggleUserStatus(int userId) {
	        try (Connection con = DbConnection.getConnection()) {
	            String query = "UPDATE users SET is_active = NOT is_active WHERE id = ?";
	            try (PreparedStatement ps = con.prepareStatement(query)) {
	                ps.setInt(1, userId);
	                ps.executeUpdate();
	                System.out.println("✅ User status toggled: ID " + userId);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    // Change user role (admin or user)
	    private void changeUserRole(int userId, String role) {
	        try (Connection con = DbConnection.getConnection()) {
	            String query = "UPDATE users SET role = ? WHERE id = ?";
	            try (PreparedStatement ps = con.prepareStatement(query)) {
	                ps.setString(1, role);
	                ps.setInt(2, userId);
	                ps.executeUpdate();
	                System.out.println("✅ User role changed to " + role + ": ID " + userId);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

