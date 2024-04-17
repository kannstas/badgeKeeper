package dao;

import model.Employee;
import model.EmployeePersonalData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.generator.employee.EmployeeGenerator.generateEmployee;
import static util.generator.employeePersonalData.EmployeePersonalDataGenerator.generateEmployeePersonalData;

public class EmployeePersonalDataDaoTest extends AbstractPostgresContainerTest {
    private Employee employee;
    private EmployeePersonalData employeePersonalData;
    private EmployeePersonalDataDAO employeePersonalDataDAO;
    private EmployeeDAO employeeDAO;

    @BeforeEach()
    public void setup() {
        employee = generateEmployee();
        employeePersonalData = generateEmployeePersonalData(employee.id());
        employeePersonalDataDAO = new EmployeePersonalDataDAO();
        employeeDAO = new EmployeeDAO();

        truncateCascade("badges");
        truncateCascade("employees");
        truncateCascade("employees_personal_data");
    }

    @Test
    void testGetEmployeePersonalDataByIdSuccess() {
        employeeDAO.save(employee);

        employeePersonalDataDAO.save(employeePersonalData);

        EmployeePersonalData foundPersonalData = employeePersonalDataDAO.get(employeePersonalData.employeeId()).orElseThrow();
        assertThat(foundPersonalData).isEqualTo(employeePersonalData);
    }

    @Test
    void testEmployeePersonalDataGetAllSuccess() {
        employeeDAO.save(employee);

        employeePersonalDataDAO.save(employeePersonalData);

        List<EmployeePersonalData> personalDataList = employeePersonalDataDAO.getAll();

        EmployeePersonalData foundPersonalData = personalDataList.stream()
                .findFirst()
                .orElseThrow();

        assertEquals(personalDataList.size(), 1);
        assertThat(foundPersonalData).isEqualTo(employeePersonalData);
    }

    @Test
    void testEmployeePersonalDataSaveSuccess() {
        employeeDAO.save(employee);

        employeePersonalDataDAO.save(employeePersonalData);

        EmployeePersonalData foundPersonalData = employeePersonalDataDAO.get(employeePersonalData.employeeId()).orElseThrow();

        assertThat(foundPersonalData).isEqualTo(employeePersonalData);
    }

    @Test
    void testEmployeePersonalDataUpdateSuccess() {
        employeeDAO.save(employee);

        employeePersonalDataDAO.save(employeePersonalData);

        EmployeePersonalData updatePersonalData = new EmployeePersonalData(
                employeePersonalData.id(),
                employeePersonalData.employeeId(),
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                20,
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                randomAlphanumeric(10)
        );

        employeePersonalDataDAO.update(updatePersonalData);

        EmployeePersonalData result = employeePersonalDataDAO.get(updatePersonalData.employeeId()).orElseThrow();

        assertThat(result).isEqualTo(updatePersonalData);
    }

    @Test
    void testEmployeePersonalDataDeleteSuccess() {
        employeeDAO.save(employee);

        employeePersonalDataDAO.save(employeePersonalData);

        employeePersonalDataDAO.delete(employeePersonalData.id());

        EmployeePersonalData foundPersonalData = employeePersonalDataDAO.get(employeePersonalData.employeeId()).orElse(null);
        Employee foundEmployee = employeeDAO.get(employee.id()).orElseThrow();

        assertThat(foundPersonalData).isEqualTo(null);
        assertThat(foundEmployee).isEqualTo(employee);

    }

}
