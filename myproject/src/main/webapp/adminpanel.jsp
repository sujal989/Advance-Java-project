<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="virinchi.model.UserTable, virinchi.util.DbConnection, java.sql.*" %>
<%@ page session="true" %>
<%
    UserTable loggedUser = (UserTable) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Panel - EliteWear</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }
        .container { max-width: 1400px; margin: 0 auto; }
        .header { background: white; padding: 20px 30px; border-radius: 10px; margin-bottom: 30px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); display: flex; justify-content: space-between; align-items: center; }
        .header h1 { color: #667eea; }
        .user-info { display: flex; gap: 20px; align-items: center; }
        .logout-btn { background: #f56565; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin-bottom: 30px; }
        .stat-card { background: white; padding: 25px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); }
        .stat-card h3 { color: #667eea; margin-bottom: 10px; }
        .stat-card .number { font-size: 36px; font-weight: bold; color: #2d3748; }
        .users-table { background: white; border-radius: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); overflow: hidden; margin-bottom: 30px; }
        .table-header { padding: 20px 30px; border-bottom: 2px solid #e2e8f0; }
        table { width: 100%; border-collapse: collapse; }
        th { padding: 15px; text-align: left; background: #f7fafc; font-weight: 600; color: #4a5568; }
        td { padding: 15px; border-top: 1px solid #e2e8f0; }
        tbody tr:hover { background: #f7fafc; }
        .badge { padding: 4px 12px; border-radius: 12px; font-size: 12px; font-weight: 600; }
        .badge-admin { background: #fef5e7; color: #d97706; }
        .badge-user { background: #e0f2fe; color: #0284c7; }
        .badge-active { background: #d1fae5; color: #059669; }
        .badge-inactive { background: #fee2e2; color: #dc2626; }
        .action-btn { padding: 6px 12px; margin: 0 3px; border: none; border-radius: 4px; cursor: pointer; font-size: 12px; color: white; }
        .btn-delete { background: #ef4444; }
        .btn-toggle { background: #fbbf24; }
        .btn-admin { background: #8b5cf6; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🛡️ Admin Dashboard</h1>
            <div class="nav-links">
                <a href="admin">Users</a>
                <a href="products">Products</a>
            </div>
            <div class="user-info">
                <span>Welcome, <strong><%= loggedUser.getUsername() %></strong></span>
                <a href="logout" class="logout-btn">Logout</a>
            </div>
        </div>
        
        <%
            int totalUsers = 0, activeUsers = 0, totalProducts = 0;
            Connection statCon = null;
            try {
                statCon = DbConnection.getConnection();
                PreparedStatement ps1 = statCon.prepareStatement("SELECT COUNT(*) FROM users");
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) totalUsers = rs1.getInt(1);
                
                PreparedStatement ps2 = statCon.prepareStatement("SELECT COUNT(*) FROM users WHERE is_active = TRUE");
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) activeUsers = rs2.getInt(1);
                
                PreparedStatement ps3 = statCon.prepareStatement("SELECT COUNT(*) FROM products WHERE is_active = TRUE");
                ResultSet rs3 = ps3.executeQuery();
                if (rs3.next()) totalProducts = rs3.getInt(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (statCon != null) statCon.close();
            }
        %>
        
        <div class="stats-grid">
            <div class="stat-card"><h3>Total Users</h3><div class="number"><%= totalUsers %></div></div>
            <div class="stat-card"><h3>Active Users</h3><div class="number"><%= activeUsers %></div></div>
            <div class="stat-card"><h3>Total Products</h3><div class="number"><%= totalProducts %></div></div>
        </div>
        
        <div class="users-table">
            <div class="table-header"><h2>User Management</h2></div>
            <table>
                <thead>
                    <tr><th>ID</th><th>Username</th><th>Email</th><th>Role</th><th>Status</th><th>Actions</th></tr>
                </thead>
                <tbody>
                    <%
                        Connection con = null;
                        PreparedStatement ps = null;
                        ResultSet rs = null;
                        try {
                            con = DbConnection.getConnection();
                            ps = con.prepareStatement("SELECT id, username, email, role, is_active FROM users ORDER BY id ASC");
                            rs = ps.executeQuery();
                            
                            while (rs.next()) {
                                int id = rs.getInt("id");
                                String username = rs.getString("username");
                                String email = rs.getString("email");
                                String role = rs.getString("role");
                                boolean isActive = rs.getBoolean("is_active");
                    %>
                    <tr>
                        <td><%= id %></td>
                        <td><%= username %></td>
                        <td><%= email %></td>
                        <td><span class="badge <%= "admin".equals(role) ? "badge-admin" : "badge-user" %>"><%= role != null ? role.toUpperCase() : "USER" %></span></td>
                        <td><span class="badge <%= isActive ? "badge-active" : "badge-inactive" %>"><%= isActive ? "Active" : "Inactive" %></span></td>
                        <td>
                            <form method="post" action="admin" style="display:inline;">
                                <input type="hidden" name="userId" value="<%= id %>">
                                <input type="hidden" name="action" value="toggleStatus">
                                <button type="submit" class="action-btn btn-toggle">Toggle</button>
                            </form>
                            <% if (!"admin".equals(role)) { %>
                            <form method="post" action="admin" style="display:inline;">
                                <input type="hidden" name="userId" value="<%= id %>">
                                <input type="hidden" name="action" value="makeAdmin">
                                <button type="submit" class="action-btn btn-admin">Make Admin</button>
                            </form>
                            <% } else { %>
                            <form method="post" action="admin" style="display:inline;">
                                <input type="hidden" name="userId" value="<%= id %>">
                                <input type="hidden" name="action" value="removeAdmin">
                                <button type="submit" class="action-btn btn-admin">Remove Admin</button>
                            </form>
                            <% } %>
                            <form method="post" action="admin" style="display:inline;" onsubmit="return confirm('Delete this user?');">
                                <input type="hidden" name="userId" value="<%= id %>">
                                <input type="hidden" name="action" value="deleteUser">
                                <button type="submit" class="action-btn btn-delete">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <%
                            }
                        } catch (Exception e) {
                            out.println("<tr><td colspan='6' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
                            e.printStackTrace();
                        } finally {
                            if (rs != null) rs.close();
                            if (ps != null) ps.close();
                            if (con != null) con.close();
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>