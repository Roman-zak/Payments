package db;
//import com.mchange.v2.c3p0.ComboPooledDataSource;
import dao.CardDAO;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {
    private static final Logger logger = Logger.getLogger(DBCPDataSource.class);
    private static BasicDataSource ds = new BasicDataSource();
    static {
            //ds.setDriverClass("com.mysql.cj.jdbc.Driver");
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ds.setUrl(Constants.PAYMENTS_CONNECTION_URL);
            ds.setUsername(Constants.PAYMENTS_DB_USER);
            ds.setPassword(Constants.PAYMENTS_DB_PASSWORD);
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
            ds.setDefaultAutoCommit(false);
    }
        public static Connection getConnection() throws  DBException {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            logger.warn("Failed to get connection",e);
            throw new DBException("Failed to get connection");
        }
        return con;
    }

    private DBCPDataSource(){}
}
