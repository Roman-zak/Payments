package com.payments.dao;

import com.payments.data.DBException;

import java.util.List;

public interface DAO<T> {

    T get(int id) throws DBException;

    List<T> getAll() throws DBException;

    void save(T t) throws DBException;

    void update(T t, String[] params);

    void delete(T t);
}
