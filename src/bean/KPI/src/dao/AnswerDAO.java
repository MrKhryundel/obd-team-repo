package dao;

import db_instances.Answer;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO implements DAO<Answer> {

    @Override
    public void create(Answer answer) throws SQLException {
        String sql = "INSERT INTO Answer (ResponseID, QuestionID, OptionID, TextAnswer) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, answer.ResponseID);
            ps.setInt(2, answer.QuestionID);
            ps.setInt(3, answer.OptionID);
            ps.setString(4, answer.TextAnswer);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) answer.AnswerID = rs.getInt(1);
        }
    }

    @Override
    public Answer findById(int id) throws SQLException {
        String sql = "SELECT * FROM Answer WHERE AnswerID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Answer a = new Answer();
                a.AnswerID = rs.getInt("AnswerID");
                a.ResponseID = rs.getInt("ResponseID");
                a.QuestionID = rs.getInt("QuestionID");
                a.OptionID = rs.getInt("OptionID");
                a.TextAnswer = rs.getString("TextAnswer");
                return a;
            }
        }
        return null;
    }

    @Override
    public List<Answer> findAll() throws SQLException {
        String sql = "SELECT * FROM Answer";
        List<Answer> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Answer a = new Answer();
                a.AnswerID = rs.getInt("AnswerID");
                a.ResponseID = rs.getInt("ResponseID");
                a.QuestionID = rs.getInt("QuestionID");
                a.OptionID = rs.getInt("OptionID");
                a.TextAnswer = rs.getString("TextAnswer");
                list.add(a);
            }
        }
        return list;
    }

    @Override
    public void update(Answer answer) throws SQLException {
        String sql = "UPDATE Answer SET ResponseID=?, QuestionID=?, OptionID=?, TextAnswer=? WHERE AnswerID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, answer.ResponseID);
            ps.setInt(2, answer.QuestionID);
            ps.setInt(3, answer.OptionID);
            ps.setString(4, answer.TextAnswer);
            ps.setInt(5, answer.AnswerID);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Answer WHERE AnswerID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
