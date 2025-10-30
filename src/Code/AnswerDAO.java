import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO extends DAO<Answer> {

    public AnswerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(Answer a) throws SQLException {
        String sql = "INSERT INTO Answer (ResponseID, QuestionID, OptionID, TextAnswer) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, a.getResponseID());
            stmt.setLong(2, a.getQuestionID());
            if (a.getOptionID() != null)
                stmt.setLong(3, a.getOptionID());
            else
                stmt.setNull(3, Types.BIGINT);
            stmt.setString(4, a.getTextAnswer());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                a.setAnswerID(keys.getLong(1));
            }
        }
    }

    @Override
    public List<Answer> getAll() throws SQLException {
        List<Answer> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Answer")) {
            while (rs.next()) {
                Answer a = new Answer();
                a.setAnswerID(rs.getLong("AnswerID"));
                a.setResponseID(rs.getLong("ResponseID"));
                a.setQuestionID(rs.getLong("QuestionID"));
                long opt = rs.getLong("OptionID");
                if (rs.wasNull()) a.setOptionID(null);
                else a.setOptionID(opt);
                a.setTextAnswer(rs.getString("TextAnswer"));
                list.add(a);
            }
        }
        return list;
    }

    @Override
    public Answer getById(Long id) throws SQLException {
        String sql = "SELECT * FROM Answer WHERE AnswerID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Answer a = new Answer();
                a.setAnswerID(rs.getLong("AnswerID"));
                a.setResponseID(rs.getLong("ResponseID"));
                a.setQuestionID(rs.getLong("QuestionID"));
                long opt = rs.getLong("OptionID");
                if (rs.wasNull()) a.setOptionID(null);
                else a.setOptionID(opt);
                a.setTextAnswer(rs.getString("TextAnswer"));
                return a;
            }
        }
        return null;
    }
}
