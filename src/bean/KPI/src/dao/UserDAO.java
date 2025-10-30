package dao;

import db_instances.User;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {

    @Override
    public void create(User user) throws SQLException {
        String sql = "INSERT INTO User (Name, Email, CreatedAt, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.Name);
            ps.setString(2, user.Email);
            ps.setString(3, user.CreatedAt);
            ps.setString(4, user.role);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) user.UserId = rs.getInt(1);
        }
    }

    @Override
    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM User WHERE UserId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.UserId = rs.getInt("UserId");
                u.Name = rs.getString("Name");
                u.Email = rs.getString("Email");
                u.CreatedAt = rs.getString("CreatedAt");
                u.role = rs.getString("role");
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM User";
        List<User> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                User u = new User();
                u.UserId = rs.getInt("UserId");
                u.Name = rs.getString("Name");
                u.Email = rs.getString("Email");
                u.CreatedAt = rs.getString("CreatedAt");
                u.role = rs.getString("role");
                users.add(u);
            }
        }
        return users;
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE User SET Name=?, Email=?, CreatedAt=?, role=? WHERE UserId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.Name);
            ps.setString(2, user.Email);
            ps.setString(3, user.CreatedAt);
            ps.setString(4, user.role);
            ps.setInt(5, user.UserId);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM User WHERE UserId=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
