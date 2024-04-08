import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import util.DBConnector;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws LiquibaseException, SQLException {

        Connection connection = new DBConnector().getConnection();

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Liquibase liquibase = new Liquibase("db/changelog.xml", new ClassLoaderResourceAccessor(), database);

        liquibase.update(new Contexts(), new LabelExpression());

      //TODO вынести в отдельный метод

    }


}