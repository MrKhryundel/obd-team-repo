import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO extends DAO<Question> {

    public QuestionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(Question q) throws SQLException {
        String sql = "INSERT INTO Question (FormID, Text, Type, IsRequired) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, q.getFormID());
            stmt.setString(2, q.getText());
            stmt.setString(3, q.getType());
            stmt.setBoolean(4, q.getIsRequired());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                q.setQuestionID(keys.getLong(1));
            }
        }
    }

    @Override
    public List<Question> getAll() throws SQLException {
        List<Question> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Question")) {
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionID(rs.getLong("QuestionID"));
                q.setFormID(rs.getLong("FormID"));
                q.setText(rs.getString("Text"));
                q.setType(rs.getString("Type"));
                q.setIsRequired(rs.getBoolean("IsRequired"));
                list.add(q);
            }
        }
        return list;
    }

    @Override
    public Question getById(Long id) throws SQLException {
        String sql = "SELECT * FROM Question WHERE QuestionID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Question q = new Question();
                q.setQuestionID(rs.getLong("QuestionID"));
                q.setFormID(rs.getLong("FormID"));
                q.setText(rs.getString("Text"));
                q.setType(rs.getString("Type"));
                q.setIsRequired(rs.getBoolean("IsRequired"));
                return q;
            }
        }
        return null;
    }
}
