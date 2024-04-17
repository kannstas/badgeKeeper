package dao;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import util.connection.DBConnector;
import util.connection.LiquibaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Testcontainers
public abstract class AbstractPostgresContainerTest {
    @Container
    private final static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @BeforeAll
    static void beforeAll() {
        try {
            postgres.start();

            DBConnector.init(
                    postgres.getJdbcUrl(),
                    postgres.getUsername(),
                    postgres.getPassword()
            );

            Connection connection = DBConnector.getConnection();
            LiquibaseConnection.startLiquibase(connection);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    protected void truncateCascade(String tableName) {
        try (PreparedStatement preparedStatement = DBConnector.getConnection()
                .prepareStatement("DELETE FROM %s".formatted(tableName))
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}