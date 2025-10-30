package dao;

import java.util.List;

public interface DAO<T> {
    void create(T obj) throws Exception;
    T findById(int id) throws Exception;
    List<T> findAll() throws Exception;
    void update(T obj) throws Exception;
    void delete(int id) throws Exception;
}
