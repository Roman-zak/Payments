package com.payments.data;

import java.sql.SQLException;

public class DBException extends SQLException {
    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
    public DBException(Throwable e) {
        super(e);
    }

    public DBException(String s) {
        super(s);

    }
}
