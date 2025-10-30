package dao;

import db_instances.Response;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponseDAO implements DAO<Response> {

    @Override
    public void create(Response response) throws SQLException {
        String sql = "INSERT INTO Response (FormID, SubmittedAt) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, response.FormID);
            ps.setTimestamp(2, response.SubmittedAt);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) response.ResponseID = rs.getInt(1);
        }
    }

    @Override
    public Response findById(int id) throws SQLException {
        String sql = "SELECT * FROM Response WHERE ResponseID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Response r = new Response();
                r.ResponseID = rs.getInt("ResponseID");
                r.FormID = rs.getInt("FormID");
                r.SubmittedAt = rs.getTimestamp("SubmittedAt");
                return r;
            }
        }
        return null;
    }

    @Override
    public List<Response> findAll() throws SQLException {
        String sql = "SELECT * FROM Response";
        List<Response> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Response r = new Response();
                r.ResponseID = rs.getInt("ResponseID");
                r.FormID = rs.getInt("FormID");
                r.SubmittedAt = rs.getTimestamp("SubmittedAt");
                list.add(r);
            }
        }
        return list;
    }

    @Override
    public void update(Response response) throws SQLException {
        String sql = "UPDATE Response SET FormID=?, SubmittedAt=? WHERE ResponseID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, response.FormID);
            ps.setTimestamp(2, response.SubmittedAt);
            ps.setInt(3, response.ResponseID);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Response WHERE ResponseID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
