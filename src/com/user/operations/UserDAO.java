package com.user.operations;

import java.sql.*;


import java.util.ArrayList;
import java.util.List;

import com.db.DBConnection;


public class UserDAO {
	public void addUser(User user) throws SQLException {
	    String sql = "INSERT INTO DB2ADMIN.USERACCESSMANAGEMENT (EMAILID, ISACTIVE, ROLE) VALUES (?, ?, ?)";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        stmt.setString(1, user.getEmailId());
	        stmt.setString(2, user.getIsActive());
	        stmt.setString(3, user.getRole());
	        stmt.executeUpdate();

	        // Get auto-generated ID
	        ResultSet generatedKeys = stmt.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            user.setId(generatedKeys.getInt(1)); 
	        }
	    }
	}


    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM DB2ADMIN.USERACCESSMANAGEMENT WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("ID"), rs.getString("EMAILID"), rs.getString("ISACTIVE"), rs.getString("ROLE"));
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM DB2ADMIN.USERACCESSMANAGEMENT";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(rs.getInt("ID"), rs.getString("EMAILID"), rs.getString("ISACTIVE"), rs.getString("ROLE")));
            }
        }
        return users;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE DB2ADMIN.USERACCESSMANAGEMENT SET EMAILID = ?, ISACTIVE = ?, ROLE = ? WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmailId());
            stmt.setString(2, user.getIsActive());
            stmt.setString(3, user.getRole());
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
        }
    }

    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM DB2ADMIN.USERACCESSMANAGEMENT WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Returns true if a row was deleted, false if not found
        }
    }

}
