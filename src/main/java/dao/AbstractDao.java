package dao;

import util.connection.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class AbstractDao<MODEL, KEY> {

    protected List<MODEL> fetch(String sql) {
        final List<MODEL> results = new ArrayList<>();


        try (PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement(sql);
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

    protected void executeUpdate(String sql, Map<Integer, Object> params) {
        Connection connection;
        try {
            connection = DBConnector.getConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Integer paramKey : params.keySet()) {
                preparedStatement.setObject(paramKey, params.get(paramKey));
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void executeUpdate(String sql) {
        try (PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement(sql)) {

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    abstract MODEL resultSetToModel(ResultSet resultSet) throws SQLException;

}