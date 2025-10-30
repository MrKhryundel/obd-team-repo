package dao;

import db_instances.AnswerOption;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerOptionDAO implements DAO<AnswerOption> {

    @Override
    public void create(AnswerOption option) throws SQLException {
        String sql = "INSERT INTO AnswerOption (QuestionID, OptionText) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, option.QuestionID);
            ps.setString(2, option.OptionText);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) option.OptionID = rs.getInt(1);
        }
    }

    @Override
    public AnswerOption findById(int id) throws SQLException {
        String sql = "SELECT * FROM AnswerOption WHERE OptionID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AnswerOption o = new AnswerOption();
                o.OptionID = rs.getInt("OptionID");
                o.QuestionID = rs.getInt("QuestionID");
                o.OptionText = rs.getString("OptionText");
                return o;
            }
        }
        return null;
    }

    @Override
    public List<AnswerOption> findAll() throws SQLException {
        String sql = "SELECT * FROM AnswerOption";
        List<AnswerOption> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                AnswerOption o = new AnswerOption();
                o.OptionID = rs.getInt("OptionID");
                o.QuestionID = rs.getInt("QuestionID");
                o.OptionText = rs.getString("OptionText");
                list.add(o);
            }
        }
        return list;
    }

    @Override
    public void update(AnswerOption option) throws SQLException {
        String sql = "UPDATE AnswerOption SET QuestionID=?, OptionText=? WHERE OptionID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, option.QuestionID);
            ps.setString(2, option.OptionText);
            ps.setInt(3, option.OptionID);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM AnswerOption WHERE OptionID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
