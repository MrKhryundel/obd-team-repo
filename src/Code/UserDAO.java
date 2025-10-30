import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO<User> {

    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO User (Name, Email, Role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                user.setUserID(keys.getLong(1));
            }
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM User")) {
            while (rs.next()) {
                User u = new User();
                u.setUserID(rs.getLong("UserID"));
                u.setName(rs.getString("Name"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getString("Role"));
                u.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(u);
            }
        }
        return list;
    }

    @Override
    public User getById(Long id) throws SQLException {
        String sql = "SELECT * FROM User WHERE UserID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserID(rs.getLong("UserID"));
                u.setName(rs.getString("Name"));
                u.setEmail(rs.getString("Email"));
                u.setRole(rs.getString("Role"));
                u.setCreatedAt(rs.getTimestamp("CreatedAt"));
                return u;
            }
        }
        return null;
    }
}

