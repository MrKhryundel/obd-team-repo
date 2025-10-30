import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerOptionDAO extends DAO<AnswerOption> {

    public AnswerOptionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(AnswerOption ao) throws SQLException {
        String sql = "INSERT INTO AnswerOption (QuestionID, OptionText) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, ao.getQuestionID());
            stmt.setString(2, ao.getOptionText());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                ao.setOptionID(keys.getLong(1));
            }
        }
    }

    @Override
    public List<AnswerOption> getAll() throws SQLException {
        List<AnswerOption> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM AnswerOption")) {
            while (rs.next()) {
                AnswerOption ao = new AnswerOption();
                ao.setOptionID(rs.getLong("OptionID"));
                ao.setQuestionID(rs.getLong("QuestionID"));
                ao.setOptionText(rs.getString("OptionText"));
                list.add(ao);
            }
        }
        return list;
    }

    @Override
    public AnswerOption getById(Long id) throws SQLException {
        String sql = "SELECT * FROM AnswerOption WHERE OptionID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                AnswerOption ao = new AnswerOption();
                ao.setOptionID(rs.getLong("OptionID"));
                ao.setQuestionID(rs.getLong("QuestionID"));
                ao.setOptionText(rs.getString("OptionText"));
                return ao;
            }
        }
        return null;
    }
}
