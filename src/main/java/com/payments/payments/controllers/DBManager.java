package com.payments.payments.controllers;

import dao.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = DriverManager.getConnection(Constants.PAYMENTS_CONNECTION_URL,
                Constants.PAYMENTS_DB_USER, Constants.PAYMENTS_DB_PASSWORD);
        connection.setAutoCommit(false);
        System.out.println(connection);
        return connection;
    }
}
