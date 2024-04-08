package dao;

import jakarta.inject.Inject;
import util.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract class AbstractDao<MODEL, KEY> {

    protected List<MODEL> fetch(String sql) {
        final List<MODEL> results = new ArrayList<>();

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                results.add(resultSetToModel(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    protected void executeUpdate(String sql, Object... params) {
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean checkContains(String sql, Object...params) {
      //проверка, что вернулось из
        return true;
    }

    abstract MODEL resultSetToModel(ResultSet resultSet) throws SQLException;

}