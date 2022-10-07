package db;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3p0DataSource {
    private static ComboPooledDataSource cpds = new ComboPooledDataSource();
        public static Connection getConnection() throws  DBException {
        Connection con = null;

        try {
            cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
            cpds.setJdbcUrl(Constants.PAYMENTS_CONNECTION_URL);
            cpds.setUser(Constants.PAYMENTS_DB_USER);
            cpds.setPassword(Constants.PAYMENTS_DB_PASSWORD);
            con = cpds.getConnection();
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DBException("Failed to get connection");
        } catch (PropertyVetoException e) {
            throw new DBException("Connection pool exception");

        }

        return con;
    }

    private C3p0DataSource(){}
}
