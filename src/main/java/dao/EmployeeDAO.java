package dao;

import api.common.Position;
import model.Employee;
import util.dao.DAOUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EmployeeDAO extends AbstractDao<Employee, UUID> {

    private final String SELECT_EMPLOYEE =
            """
                    SELECT
                      id,
                      position,
                      department,
                      created_at,
                      updated_at
                    FROM employees
                    """;


    public Optional<Employee> get(UUID id) {
        String sql = "%s WHERE id='%s'".formatted(SELECT_EMPLOYEE, id);

        return DAOUtils.extractSingleResultOrNull(
                fetch(sql)
        );
    }

    public List<Employee> getAll() {
        return fetch(SELECT_EMPLOYEE);
    }

    public void save(Employee employee) {
        executeUpdate(
                """
                        INSERT INTO employees (
                          id,
                          position,
                          department,
                          created_at,
                          updated_at
                        ) VALUES (?,?,?,?,?)
                        """,
                Map.of(
                        1, employee.id(),
                        2, employee.position().name(),
                        3, employee.department(),
                        4, DAOUtils.toTimestamp(employee.createdAt()),
                        5, DAOUtils.toTimestamp(employee.updatedAt())
                )
        );

    }

    public void update(UUID id, Employee employee) {

        executeUpdate(
                """
                        UPDATE employees
                        SET
                          position=?,
                          department=?,
                          updated_at=?
                        WHERE
                          id='%s';
                        """
                        .formatted(id),
                Map.of(
                        1, employee.position().name(),
                        2, employee.department(),
                        3, DAOUtils.toTimestamp(employee.updatedAt())
                )
        );
    }


    public void delete(UUID id) {
        executeUpdate(
                """
                        DELETE FROM employees
                        WHERE id='%s';
                        """
                        .formatted(id)
        );
    }

    @Override
    Employee resultSetToModel(ResultSet resultSet) throws SQLException {
        return new Employee(
                (UUID) resultSet.getObject("id"),
                Enum.valueOf(Position.class, resultSet.getString("position")),
                resultSet.getString("department"),
                resultSet.getTimestamp("created_at").toInstant(),
                resultSet.getTimestamp("updated_at").toInstant()
        );
    }


}