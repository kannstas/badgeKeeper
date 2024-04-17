package dao;

import model.EmployeePersonalData;
import util.dao.DAOUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class EmployeePersonalDataDAO extends AbstractDao<EmployeePersonalData, UUID> {

    private final String SELECT_EMPLOYEE_PERSONAL_DATA =
            """
                    SELECT
                        id,
                        employee_id,
                        first_name,
                        last_name,
                        middle_name,
                        age,
                        address,
                        phone_number,
                        email
                     FROM employees_personal_data
                     """;


    public Optional<EmployeePersonalData> get(UUID id) {

        String sql = "%s WHERE employee_id='%s'".formatted(SELECT_EMPLOYEE_PERSONAL_DATA, id);
        return DAOUtils.extractSingleResultOrNull(
                fetch(sql)
        );
    }

    public List<EmployeePersonalData> getAll() {
        return fetch(SELECT_EMPLOYEE_PERSONAL_DATA);
    }

    public void save(EmployeePersonalData employeePersonalData) {
        executeUpdate(
                """
                        INSERT INTO employees_personal_data(
                           id,
                           employee_id,
                           first_name,
                           last_name,
                           middle_name,
                           age,
                           address,
                           phone_number,
                           email)
                           VALUES (?,?,?,?,?,?,?,?,?)
                        """,
                Map.of(
                        1, employeePersonalData.id(),
                        2, employeePersonalData.employeeId(),
                        3, employeePersonalData.firstName(),
                        4, employeePersonalData.lastName(),
                        5, employeePersonalData.middleName(),
                        6, employeePersonalData.age(),
                        7, employeePersonalData.address(),
                        8, employeePersonalData.phoneNumber(),
                        9, employeePersonalData.email()
                )
        );
    }

    public void update(EmployeePersonalData employeePersonalData) {

        executeUpdate(
                """
                        UPDATE employees_personal_data
                        SET
                            employee_id=?,
                            first_name=?,
                            last_name=?,
                            middle_name=?,
                            age=?,
                            address=?,
                            phone_number=?,
                            email=?
                        WHERE
                             id='%s'
                         """
                        .formatted(employeePersonalData.id()),
                Map.of(
                        1, employeePersonalData.employeeId(),
                        2, employeePersonalData.firstName(),
                        3, employeePersonalData.lastName(),
                        4, employeePersonalData.middleName(),
                        5, employeePersonalData.age(),
                        6, employeePersonalData.address(),
                        7, employeePersonalData.phoneNumber(),
                        8, employeePersonalData.email()
                )
        );
    }

    public void delete(UUID id) {
        executeUpdate(
                """
                        DELETE FROM employees_personal_data
                        WHERE id='%s';
                        """
                        .formatted(id)
        );
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