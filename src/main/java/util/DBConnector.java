package util;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {
    private static final String URL = "jdbc:postgresql://localhost:5432/badge_keeper_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "19691970";
    private static final DataSource dataSource = new DataSource();

    static {
        PoolProperties pool = new PoolProperties();
        pool.setUrl(URL);
        pool.setUsername(USERNAME);
        pool.setPassword(PASSWORD);
        pool.setInitialSize(3);
        pool.setDriverClassName("org.postgresql.Driver");
        dataSource.setPoolProperties(pool);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

