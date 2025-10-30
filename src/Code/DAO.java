import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Абстрактний клас DAO
public abstract class DAO<T> {
    protected Connection connection;

    public DAO(Connection connection) {
        this.connection = connection;
    }

    public abstract void insert(T obj) throws SQLException;
    public abstract List<T> getAll() throws SQLException;
    public abstract T getById(Long id) throws SQLException;
}

