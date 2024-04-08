package dao;

import jakarta.inject.Inject;
import model.EmployeePersonalData;
import util.DAOUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EmployeePersonalDataDAO extends AbstractDao<EmployeePersonalData, UUID> {

    @Inject
    private  DAOUtil daoUtil;

    private final String SELECT_EMPLOYEE_PERSONAL_DATA = """
            SELECT id, employee_id, first_name, last_name, middle_name, age, address, phone_number, email
            FROM employees_personal_data
            """;

    private final String INSERT_EMPLOYEE_PERSONAL_DATA = """
            INSERT INTO employees_personal_data (id, employee_id, first_name, last_name, middle_name, age, address, phone_number, email)
            VALUES (?,?,?,?,?,?,?,?,?)
            """;

    private final String UPDATE_EMPLOYEE_PERSONAL_DATA = """
            UPDATE employees_personal_data
            SET employee_id=?, first_name=?, last_name=?, middle_name=?, age=?, address=?, phone_number=?, email=?
            """;

    private final String DELETE_EMPLOYEE_PERSONAL_DATA = """
            DELETE FROM employees_personal_data
            """;

    public Optional<EmployeePersonalData> get(UUID id) {
        String sql = "%s WHERE employee_id='%s'".formatted(SELECT_EMPLOYEE_PERSONAL_DATA,id);
        return daoUtil.extractSingleResultOrNull(
                        fetch(sql)
                );
    }
    public Optional<EmployeePersonalData> getEmployeePersonalData(UUID id) {
        String sql = "%s WHERE id='%s'".formatted(SELECT_EMPLOYEE_PERSONAL_DATA, id);
        return daoUtil.extractSingleResultOrNull(
                fetch(sql)
        );
    }

    public List<EmployeePersonalData> getAll() {
        return fetch(SELECT_EMPLOYEE_PERSONAL_DATA);
    }

    public void save(EmployeePersonalData employeePersonalData) {
        executeUpdate(
                INSERT_EMPLOYEE_PERSONAL_DATA,
                employeePersonalData.id(),
                employeePersonalData.employeeId(),
                employeePersonalData.firstName(),
                employeePersonalData.lastName(),
                employeePersonalData.middleName(),
                employeePersonalData.age(),
                employeePersonalData.address(),
                employeePersonalData.phoneNumber(),
                employeePersonalData.email()
        );
    }

    public void update(EmployeePersonalData employeePersonalData) {
        String sql = "%s WHERE id='%s'".formatted(UPDATE_EMPLOYEE_PERSONAL_DATA, employeePersonalData.id());
        executeUpdate(
                sql,
                employeePersonalData.employeeId(),
                employeePersonalData.firstName(),
                employeePersonalData.lastName(),
                employeePersonalData.middleName(),
                employeePersonalData.age(),
                employeePersonalData.address(),
                employeePersonalData.phoneNumber(),
                employeePersonalData.email()
        );
    }

    public void delete(UUID id) {
        String sql = " %s WHERE id='%s'".formatted(DELETE_EMPLOYEE_PERSONAL_DATA, id);
        executeUpdate(sql, id);
    }

    @Override
    EmployeePersonalData resultSetToModel(ResultSet resultSet) throws SQLException {
        return new EmployeePersonalData(
                (UUID) resultSet.getObject("id"),
                (UUID) resultSet.getObject("employee_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("middle_name"),
                resultSet.getInt("age"),
                resultSet.getString("address"),
                resultSet.getString("phone_number"),
                resultSet.getString("email")
        );
    }
}