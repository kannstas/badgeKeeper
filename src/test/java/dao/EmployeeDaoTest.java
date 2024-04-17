package dao;

import api.common.Position;
import model.Employee;
import model.EmployeePersonalData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.generator.employee.EmployeeGenerator.generateEmployee;
import static util.generator.employeePersonalData.EmployeePersonalDataGenerator.generateEmployeePersonalData;

public class EmployeeDaoTest extends AbstractPostgresContainerTest {
    private EmployeeDAO employeeDAO;
    private Employee employee;

    @BeforeEach()
    public void setup() {
        employeeDAO = new EmployeeDAO();
        employee = generateEmployee();
        truncateCascade("badges");
        truncateCascade("employees");
    }

    @Test
    void testGetByIdEmployeeSuccess() {

        employeeDAO.save(employee);

        Employee foundEmployee = employeeDAO.get(employee.id()).orElseThrow();

        assertThat(foundEmployee).isEqualTo(employee);
    }

    @Test
    void testGetAllEmployeesSuccess() {
        Employee firstEmployee = generateEmployee();

        Employee secondEmployee = generateEmployee();

        employeeDAO.save(firstEmployee);
        employeeDAO.save(secondEmployee);

        List<Employee> foundEmployees = employeeDAO.getAll();

        assertEquals(2, foundEmployees.size());

        boolean allEmployeeFound = foundEmployees.stream()
                .allMatch(foundEmployees::contains);

        assertTrue(allEmployeeFound);
    }

    @Test
    void testSaveSuccess() {

        employeeDAO.save(employee);

        Employee foundEmployee = employeeDAO.get(employee.id()).orElseThrow();

        assertThat(foundEmployee).isEqualTo(employee);

    }

    @Test
    void testUpdateEmployeeSuccess() {
        employeeDAO.save(employee);

        Employee employeeForUpdate = generateEmployee(
                employee.id(),
                Position.MANAGER,
                randomAlphanumeric(10),
                employee.createdAt(),
                Instant.now().truncatedTo(ChronoUnit.MILLIS)
        );

        employeeDAO.update(employee.id(), employeeForUpdate);

        Employee result = employeeDAO.get(employee.id()).orElseThrow();

        assertThat(result).isEqualTo(employeeForUpdate);

    }

    @Test
    void testDeleteSuccess() {

        EmployeePersonalData employeePersonalData = generateEmployeePersonalData(employee.id());
        EmployeePersonalDataDAO employeePersonalDataDAO = new EmployeePersonalDataDAO();

        employeeDAO.save(employee);
        employeeDAO.delete(employee.id());

        Employee foundEmployee = employeeDAO.get(employee.id()).orElse(null);
        EmployeePersonalData foundPersonalData = employeePersonalDataDAO.get(employeePersonalData.employeeId()).orElse(null);

        assertThat(foundEmployee).isNull();
        assertThat(foundPersonalData).isNull();
    }
}