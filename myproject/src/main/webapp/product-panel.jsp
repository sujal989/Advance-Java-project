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
    <title>Product Management - EliteWear</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; padding: 20px; }
        .container { max-width: 1400px; margin: 0 auto; }
        .header { background: white; padding: 20px 30px; border-radius: 10px; margin-bottom: 30px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); display: flex; justify-content: space-between; align-items: center; }
        .header h1 { color: #667eea; }
        .nav-links { display: flex; gap: 15px; }
        .nav-links a { background: #667eea; color: white; padding: 10px 20px; border-radius: 5px; text-decoration: none; }
        .logout-btn { background: #f56565; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin-bottom: 30px; }
        .stat-card { background: white; padding: 25px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); }
        .stat-card h3 { color: #667eea; margin-bottom: 10px; }
        .stat-card .number { font-size: 36px; font-weight: bold; color: #2d3748; }
        .add-product-form { background: white; padding: 30px; border-radius: 10px; margin-bottom: 30px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); }
        .add-product-form h2 { color: #667eea; margin-bottom: 20px; }
        .form-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 15px; }
        .form-group { display: flex; flex-direction: column; }
        .form-group label { margin-bottom: 5px; color: #4a5568; font-weight: 600; }
        .form-group input, .form-group textarea { padding: 10px; border: 1px solid #e2e8f0; border-radius: 5px; font-size: 14px; }
        .form-group textarea { grid-column: 1 / -1; resize: vertical; min-height: 80px; }
        .btn-submit { background: #48bb78; color: white; padding: 12px 30px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; font-weight: 600; grid-column: 1 / -1; }
        .products-table { background: white; border-radius: 10px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); overflow: hidden; }
        .table-header { padding: 20px 30px; border-bottom: 2px solid #e2e8f0; }
        table { width: 100%; border-collapse: collapse; }
        th { padding: 15px; text-align: left; background: #f7fafc; font-weight: 600; color: #4a5568; }
        td { padding: 15px; border-top: 1px solid #e2e8f0; }
        tbody tr:hover { background: #f7fafc; }
        .product-img { width: 60px; height: 60px; object-fit: cover; border-radius: 5px; }
        .badge { padding: 4px 12px; border-radius: 12px; font-size: 12px; font-weight: 600; }
        .badge-active { background: #d1fae5; color: #059669; }
        .badge-inactive { background: #fee2e2; color: #dc2626; }
        .action-btn { padding: 6px 12px; margin: 0 3px; border: none; border-radius: 4px; cursor: pointer; font-size: 12px; color: white; }
        .btn-edit { background: #4299e1; }
        .btn-delete { background: #ef4444; }
        .btn-toggle { background: #fbbf24; }
        .modal { display: none; position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); }
        .modal-content { background: white; margin: 5% auto; padding: 30px; width: 80%; max-width: 600px; border-radius: 10px; }
        .close { color: #aaa; float: right; font-size: 28px; font-weight: bold; cursor: pointer; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🛍️ Product Management</h1>
            <div class="nav-links">
                <a href="admin">User Management</a>
                <a href="products">Products</a>
                <a href="logout" class="logout-btn">Logout</a>
            </div>
        </div>
        
        <%
            int totalProducts = (Integer) request.getAttribute("totalProducts");
            int activeProducts = (Integer) request.getAttribute("activeProducts");
        %>
        
        <div class="stats-grid">
            <div class="stat-card"><h3>Total Products</h3><div class="number"><%= totalProducts %></div></div>
            <div class="stat-card"><h3>Active Products</h3><div class="number"><%= activeProducts %></div></div>
        </div>
        
        <div class="add-product-form">
            <h2>Add New Product</h2>
            <form method="post" action="products">
                <input type="hidden" name="action" value="add">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Product Name</label>
                        <input type="text" name="productName" required>
                    </div>
                    <div class="form-group">
                        <label>Price ($)</label>
                        <input type="number" step="0.01" name="price" required>
                    </div>
                    <div class="form-group">
                        <label>Stock Quantity</label>
                        <input type="number" name="stock" required>
                    </div>
                    <div class="form-group">
                        <label>Category</label>
                        <input type="text" name="category" required>
                    </div>
                    <div class="form-group">
                        <label>Image URL</label>
                        <input type="text" name="imageUrl" placeholder="images/product.jpg">
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea name="description" rows="3"></textarea>
                    </div>
                    <button type="submit" class="btn-submit">Add Product</button>
                </div>
            </form>
        </div>
        
        <div class="products-table">
            <div class="table-header"><h2>All Products</h2></div>
            <table>
                <thead>
                    <tr><th>ID</th><th>Image</th><th>Name</th><th>Price</th><th>Stock</th><th>Category</th><th>Status</th><th>Actions</th></tr>
                </thead>
                <tbody>
                    <%
                        Connection con = null;
                        PreparedStatement ps = null;
                        ResultSet rs = null;
                        try {
                            con = DbConnection.getConnection();
                            ps = con.prepareStatement("SELECT * FROM products ORDER BY id DESC");
                            rs = ps.executeQuery();
                            
                            while (rs.next()) {
                                int id = rs.getInt("id");
                                String name = rs.getString("product_name");
                                String desc = rs.getString("description");
                                double price = rs.getDouble("price");
                                int stock = rs.getInt("stock_quantity");
                                String category = rs.getString("category");
                                String imageUrl = rs.getString("image_url");
                                boolean isActive = rs.getBoolean("is_active");
                    %>
                    <tr>
                        <td><%= id %></td>
                        <td><img src="<%= imageUrl != null ? imageUrl : "images/placeholder.jpg" %>" class="product-img" alt="Product"></td>
                        <td><%= name %></td>
                        <td>$<%= String.format("%.2f", price) %></td>
                        <td><%= stock %></td>
                        <td><%= category %></td>
                        <td><span class="badge <%= isActive ? "badge-active" : "badge-inactive" %>"><%= isActive ? "Active" : "Inactive" %></span></td>
                        <td>
                            <button onclick="editProduct(<%= id %>, '<%= name %>', '<%= desc %>', <%= price %>, <%= stock %>, '<%= category %>', '<%= imageUrl %>')" class="action-btn btn-edit">Edit</button>
                            <form method="post" action="products" style="display:inline;">
                                <input type="hidden" name="productId" value="<%= id %>">
                                <input type="hidden" name="action" value="toggleStatus">
                                <button type="submit" class="action-btn btn-toggle">Toggle</button>
                            </form>
                            <form method="post" action="products" style="display:inline;" onsubmit="return confirm('Delete this product?');">
                                <input type="hidden" name="productId" value="<%= id %>">
                                <input type="hidden" name="action" value="delete">
                                <button type="submit" class="action-btn btn-delete">Delete</button>
                            </form>
                        </td>
                    </tr>
                    <%
                            }
                        } catch (Exception e) {
                            out.println("<tr><td colspan='8' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
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
    
    <div id="editModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeModal()">&times;</span>
            <h2>Edit Product</h2>
            <form method="post" action="products">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="productId" id="editId">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Product Name</label>
                        <input type="text" name="productName" id="editName" required>
                    </div>
                    <div class="form-group">
                        <label>Price ($)</label>
                        <input type="number" step="0.01" name="price" id="editPrice" required>
                    </div>
                    <div class="form-group">
                        <label>Stock</label>
                        <input type="number" name="stock" id="editStock" required>
                    </div>
                    <div class="form-group">
                        <label>Category</label>
                        <input type="text" name="category" id="editCategory" required>
                    </div>
                    <div class="form-group">
                        <label>Image URL</label>
                        <input type="text" name="imageUrl" id="editImage">
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea name="description" id="editDesc" rows="3"></textarea>
                    </div>
                    <button type="submit" class="btn-submit">Update Product</button>
                </div>
            </form>
        </div>
    </div>
    
    <script>
        function editProduct(id, name, desc, price, stock, category, image) {
            document.getElementById('editId').value = id;
            document.getElementById('editName').value = name;
            document.getElementById('editDesc').value = desc;
            document.getElementById('editPrice').value = price;
            document.getElementById('editStock').value = stock;
            document.getElementById('editCategory').value = category;
            document.getElementById('editImage').value = image;
            document.getElementById('editModal').style.display = 'block';
        }
        
        function closeModal() {
            document.getElementById('editModal').style.display = 'none';
        }
        
        window.onclick = function(event) {
            if (event.target == document.getElementById('editModal')) {
                closeModal();
            }
        }
    </script>
</body>
</html>