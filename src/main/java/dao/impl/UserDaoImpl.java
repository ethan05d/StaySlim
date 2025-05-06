package dao.impl;

import dao.UserDao;
import model.User;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    // create a new user record
    @Override
    public void create(User user) throws SQLException {
        String sql = "INSERT INTO Users (Username, Email, PasswordHash, HeightCm) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false); // start a transaction

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setDouble(4, user.getHeightCm());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }

            conn.commit(); // commit the transaction
        }
    }

    // find a user by their username (for login or checks)
    @Override
    public User findByUsername(String username) throws SQLException {
        String sql = "SELECT UserID, Username, Email, PasswordHash, HeightCm FROM Users WHERE Username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        // return null if no user found with that username
        return null;
    }

    // find a user by email
    @Override
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT UserID, Username, Email, PasswordHash, HeightCm FROM Users WHERE Email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    // fetch a user by their userID
    @Override
    public User findById(int id) throws SQLException {
        String sql = "SELECT UserID, Username, Email, PasswordHash, HeightCm FROM Users WHERE UserID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    // retrieve all users in the system (for admin page)
    @Override
    public List<User> findAll() throws SQLException {
        String sql = "SELECT UserID, Username, Email, PasswordHash, HeightCm FROM Users";
        List<User> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        }

        return users;
    }

    // delete a user by userID
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // remove all users
    @Override
    public void deleteAll() throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Users");
        }
    }

    // helper method to map a ResultSet row to a User object
    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("UserID"));
        u.setUsername(rs.getString("Username"));
        u.setEmail(rs.getString("Email"));
        u.setPasswordHash(rs.getString("PasswordHash"));
        u.setHeightCm(rs.getDouble("HeightCm"));
        return u;
    }
}
