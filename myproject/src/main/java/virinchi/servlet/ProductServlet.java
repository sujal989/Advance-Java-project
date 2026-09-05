package virinchi.servlet;



import virinchi.util.DbConnection;
import virinchi.model.UserTable;
import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in and is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        UserTable user = (UserTable) session.getAttribute("loggedUser");
        String userRole = getUserRole(user.getUsername());
        if (!"admin".equals(userRole)) {
            response.sendRedirect("index.jsp?error=unauthorized");
            return;
        }

        // Get statistics
        int totalProducts = countTotalProducts();
        int activeProducts = countActiveProducts();
        
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("activeProducts", activeProducts);

        RequestDispatcher rd = request.getRequestDispatcher("product-panel.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addProduct(request);
            response.sendRedirect("products");

        } else if ("update".equals(action)) {
            updateProduct(request);
            response.sendRedirect("products");

        } else if ("delete".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            deleteProduct(productId);
            response.sendRedirect("products");

        } else if ("toggleStatus".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            toggleProductStatus(productId);
            response.sendRedirect("products");
        }
    }

    // Get user role
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

    // Count total products
    private int countTotalProducts() {
        try (Connection con = DbConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM products";
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

    // Count active products
    private int countActiveProducts() {
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

    // Add new product
    private void addProduct(HttpServletRequest request) {
        try (Connection con = DbConnection.getConnection()) {
            String query = "INSERT INTO products (product_name, description, price, stock_quantity, category, image_url, is_active) VALUES (?, ?, ?, ?, ?, ?, TRUE)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, request.getParameter("productName"));
                ps.setString(2, request.getParameter("description"));
                ps.setDouble(3, Double.parseDouble(request.getParameter("price")));
                ps.setInt(4, Integer.parseInt(request.getParameter("stock")));
                ps.setString(5, request.getParameter("category"));
                ps.setString(6, request.getParameter("imageUrl"));
                ps.executeUpdate();
                System.out.println("✅ Product added successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update product
    private void updateProduct(HttpServletRequest request) {
        try (Connection con = DbConnection.getConnection()) {
            String query = "UPDATE products SET product_name=?, description=?, price=?, stock_quantity=?, category=?, image_url=? WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, request.getParameter("productName"));
                ps.setString(2, request.getParameter("description"));
                ps.setDouble(3, Double.parseDouble(request.getParameter("price")));
                ps.setInt(4, Integer.parseInt(request.getParameter("stock")));
                ps.setString(5, request.getParameter("category"));
                ps.setString(6, request.getParameter("imageUrl"));
                ps.setInt(7, Integer.parseInt(request.getParameter("productId")));
                ps.executeUpdate();
                System.out.println("✅ Product updated: ID " + request.getParameter("productId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete product
    private void deleteProduct(int productId) {
        try (Connection con = DbConnection.getConnection()) {
            String query = "DELETE FROM products WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, productId);
                ps.executeUpdate();
                System.out.println("✅ Product deleted: ID " + productId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Toggle product status
    private void toggleProductStatus(int productId) {
        try (Connection con = DbConnection.getConnection()) {
            String query = "UPDATE products SET is_active = NOT is_active WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, productId);
                ps.executeUpdate();
                System.out.println("✅ Product status toggled: ID " + productId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}