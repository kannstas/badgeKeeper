package util.connection;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void init(String url, String username, String password) {
        PoolProperties pool = new PoolProperties();
        pool.setUrl(url);
        pool.setUsername(username);
        pool.setPassword(password);
        pool.setInitialSize(10);
        pool.setDriverClassName("org.postgresql.Driver");
        dataSource = new DataSource();
        dataSource.setPoolProperties(pool);
    }
}

