import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponseDAO extends DAO<Response> {

    public ResponseDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(Response r) throws SQLException {
        String sql = "INSERT INTO Response (FormID) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, r.getFormID());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                r.setResponseID(keys.getLong(1));
            }
        }
    }

    @Override
    public List<Response> getAll() throws SQLException {
        List<Response> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Response")) {
            while (rs.next()) {
                Response r = new Response();
                r.setResponseID(rs.getLong("ResponseID"));
                r.setFormID(rs.getLong("FormID"));
                r.setSubmittedAt(rs.getTimestamp("SubmittedAt"));
                list.add(r);
            }
        }
        return list;
    }

    @Override
    public Response getById(Long id) throws SQLException {
        String sql = "SELECT * FROM Response WHERE ResponseID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Response r = new Response();
                r.setResponseID(rs.getLong("ResponseID"));
                r.setFormID(rs.getLong("FormID"));
                r.setSubmittedAt(rs.getTimestamp("SubmittedAt"));
                return r;
            }
        }
        return null;
    }
}
