package util.connection;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import liquibase.exception.LiquibaseException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

@Startup
@Singleton
public class RunDbConfig {

    @PostConstruct
    public void run() {
        Properties properties = new Properties();
        try (InputStream input = RunDbConfig.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            properties.load(input);
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            DBConnector.init(url, username, password);
            LiquibaseConnection.startLiquibase(DBConnector.getConnection());
        } catch (SQLException | IOException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }
}