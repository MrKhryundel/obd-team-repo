package dao;

import db_instances.Form;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormDAO implements DAO<Form> {

    @Override
    public void create(Form form) throws SQLException {
        String sql = "INSERT INTO Form (Title, Description, CreatedAt, UserID) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, form.Title);
            ps.setInt(2, form.Description);
            ps.setTimestamp(3, form.CreatedAt);
            ps.setInt(4, form.UserID);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) form.FormID = rs.getInt(1);
        }
    }

    @Override
    public Form findById(int id) throws SQLException {
        String sql = "SELECT * FROM Form WHERE FormID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Form f = new Form();
                f.FormID = rs.getInt("FormID");
                f.Title = rs.getString("Title");
                f.Description = rs.getInt("Description");
                f.CreatedAt = rs.getTimestamp("CreatedAt");
                f.UserID = rs.getInt("UserID");
                return f;
            }
        }
        return null;
    }

    @Override
    public List<Form> findAll() throws SQLException {
        String sql = "SELECT * FROM Form";
        List<Form> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Form f = new Form();
                f.FormID = rs.getInt("FormID");
                f.Title = rs.getString("Title");
                f.Description = rs.getInt("Description");
                f.CreatedAt = rs.getTimestamp("CreatedAt");
                f.UserID = rs.getInt("UserID");
                list.add(f);
            }
        }
        return list;
    }

    @Override
    public void update(Form form) throws SQLException {
        String sql = "UPDATE Form SET Title=?, Description=?, CreatedAt=?, UserID=? WHERE FormID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, form.Title);
            ps.setInt(2, form.Description);
            ps.setTimestamp(3, form.CreatedAt);
            ps.setInt(4, form.UserID);
            ps.setInt(5, form.FormID);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Form WHERE FormID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
