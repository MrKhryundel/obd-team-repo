import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormDAO extends DAO<Form> {

    public FormDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(Form form) throws SQLException {
        String sql = "INSERT INTO Form (Title, Description, UserID) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, form.getTitle());
            stmt.setString(2, form.getDescription());
            stmt.setLong(3, form.getUserID());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                form.setFormID(keys.getLong(1));
            }
        }
    }

    @Override
    public List<Form> getAll() throws SQLException {
        List<Form> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Form")) {
            while (rs.next()) {
                Form f = new Form();
                f.setFormID(rs.getLong("FormID"));
                f.setTitle(rs.getString("Title"));
                f.setDescription(rs.getString("Description"));
                f.setUserID(rs.getLong("UserID"));
                f.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(f);
            }
        }
        return list;
    }

    @Override
    public Form getById(Long id) throws SQLException {
        String sql = "SELECT * FROM Form WHERE FormID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Form f = new Form();
                f.setFormID(rs.getLong("FormID"));
                f.setTitle(rs.getString("Title"));
                f.setDescription(rs.getString("Description"));
                f.setUserID(rs.getLong("UserID"));
                f.setCreatedAt(rs.getTimestamp("CreatedAt"));
                return f;
            }
        }
        return null;
    }
}
