package dao;

import jakarta.inject.Inject;
import model.Employee;
import util.DAOUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class EmployeeDAO extends AbstractDao<Employee, UUID> {

    @Inject
    private DAOUtil daoUtil;

    private final String SELECT_EMPLOYEE = """
            SELECT id, position, department, created_at, updated_at
            FROM employees
            """;

    private final String INSERT_EMPLOYEE = """
            INSERT INTO employees (id, position, department, created_at, updated_at) VALUES (?,?,?,?,?)
            """;

    private final String UPDATE_EMPLOYEE = """
            UPDATE employees SET position=?, department=?, updated_at=?
            """;

    private final String DELETE_EMPLOYEE = """
            DELETE FROM employees
            """;

    public Optional<Employee> get(UUID id) {
        String sql = "%s WHERE id='%s'".formatted(SELECT_EMPLOYEE, id);

        return daoUtil.extractSingleResultOrNull(
                fetch(sql)
        );
    }

    public List<Employee> getAll() {
        return fetch(SELECT_EMPLOYEE);
    }

    public void save(Employee employee) {
        executeUpdate(
                INSERT_EMPLOYEE,
                employee.id(),
                employee.position(),
                employee.department(),
                employee.createdAt(),
                employee.updatedAt()
        );
    }

    public void update(UUID id, Employee employee) {
        String sql = "%s WHERE id='%s'".formatted(UPDATE_EMPLOYEE, id);
        executeUpdate(
                sql,
                employee.position(),
                employee.department(),
                employee.updatedAt()
        );
    }

    public void delete(UUID id) {
        String sql = "%s WHERE id='%s'".formatted(DELETE_EMPLOYEE,id);
        executeUpdate(sql);
    }

    @Override
    Employee resultSetToModel(ResultSet resultSet) throws SQLException {
        return new Employee(
                (UUID) resultSet.getObject("id"),
                resultSet.getString("position"),
                resultSet.getString("department"),
                resultSet.getTimestamp("created_at"),
                resultSet.getTimestamp("updated_at")
        );
    }


}