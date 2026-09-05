package virinchi.controller;

import java.sql.*;
import virinchi.model.UserTable;
import virinchi.util.DbConnection;
import org.apache.commons.codec.digest.DigestUtils;

public class UserControllerImplement implements UserController {

    // Signup method with password hashing
    @Override
    public boolean userSignup(String username, String email, String password) {
        try (Connection con = DbConnection.getConnection()) {

            // 1️⃣ Check if username OR email already exists
            String checkQuery = "SELECT * FROM users WHERE username=? OR email=?";
            try (PreparedStatement psCheck = con.prepareStatement(checkQuery)) {
                psCheck.setString(1, username.trim());
                psCheck.setString(2, email.trim());
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("❌ User already exists: " + username + " / " + email);
                        return false;
                    }
                }
            }

            // 2️⃣ Hash the password using SHA-256
            String hashedPassword = DigestUtils.sha256Hex(password.trim());
            System.out.println("🔐 Hashing password for: " + username);

            // 3️⃣ Insert new user with hashed password
            String insertQuery = "INSERT INTO users(username, email, password, role, is_active) VALUES(?,?,?,?,?)";
            try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
                ps.setString(1, username.trim());
                ps.setString(2, email.trim());
                ps.setString(3, hashedPassword);
                ps.setString(4, "user"); // Default role
                ps.setInt(5, 1); // Active by default (use INT instead of BOOLEAN)
                int result = ps.executeUpdate();
                if (result > 0) {
                    System.out.println("✅ Signup successful for: " + username);
                    return true;
                } else {
                    System.out.println("❌ Signup failed for: " + username);
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login method with password verification
    @Override
    public UserTable userLogin(String username, String password) {
        try (Connection con = DbConnection.getConnection()) {

            String loginQuery = "SELECT * FROM users WHERE username=?";
            try (PreparedStatement ps = con.prepareStatement(loginQuery)) {
                ps.setString(1, username.trim());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String storedHashedPassword = rs.getString("password");
                        String inputHashedPassword = DigestUtils.sha256Hex(password.trim());
                        
                        System.out.println("🔐 Verifying password for: " + username);
                        
                        // Verify password by comparing hashes
                        if (storedHashedPassword.equals(inputHashedPassword)) {
                            UserTable user = new UserTable();
                            user.setId(rs.getInt("id"));
                            user.setUsername(rs.getString("username"));
                            user.setEmail(rs.getString("email"));
                            user.setPassword(storedHashedPassword);
                            System.out.println("✅ Login successful: " + username);
                            return user;
                        } else {
                            System.out.println("❌ Invalid password for: " + username);
                            return null;
                        }
                    } else {
                        System.out.println("❌ User not found: " + username);
                        return null;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}