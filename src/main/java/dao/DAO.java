package dao;

import db.DBException;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    T get(int id) throws DBException;

    List<T> getAll();

    void save(T t) throws DBException;

    void update(T t, String[] params);

    void delete(T t);
}
