package dao;

import db_instances.Question;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO implements DAO<Question> {

    @Override
    public void create(Question question) throws SQLException {
        String sql = "INSERT INTO Question (FormID, Text, Type, IsRequired) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, question.FormID);
            ps.setString(2, question.Text);
            ps.setString(3, question.Type);
            ps.setBoolean(4, question.IsRequired);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) question.QuestionID = rs.getInt(1);
        }
    }

    @Override
    public Question findById(int id) throws SQLException {
        String sql = "SELECT * FROM Question WHERE QuestionID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Question q = new Question();
                q.QuestionID = rs.getInt("QuestionID");
                q.FormID = rs.getInt("FormID");
                q.Text = rs.getString("Text");
                q.Type = rs.getString("Type");
                q.IsRequired = rs.getBoolean("IsRequired");
                return q;
            }
        }
        return null;
    }

    @Override
    public List<Question> findAll() throws SQLException {
        String sql = "SELECT * FROM Question";
        List<Question> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Question q = new Question();
                q.QuestionID = rs.getInt("QuestionID");
                q.FormID = rs.getInt("FormID");
                q.Text = rs.getString("Text");
                q.Type = rs.getString("Type");
                q.IsRequired = rs.getBoolean("IsRequired");
                list.add(q);
            }
        }
        return list;
    }

    @Override
    public void update(Question question) throws SQLException {
        String sql = "UPDATE Question SET FormID=?, Text=?, Type=?, IsRequired=? WHERE QuestionID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, question.FormID);
            ps.setString(2, question.Text);
            ps.setString(3, question.Type);
            ps.setBoolean(4, question.IsRequired);
            ps.setInt(5, question.QuestionID);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Question WHERE QuestionID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
