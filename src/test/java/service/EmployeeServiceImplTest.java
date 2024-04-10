package service;

import api.request.employee.CreateEmployeeRequest;
import api.request.employee.UpdateEmployeeRequest;
import api.response.employee.GetEmployeeResponse;
import dao.EmployeeDAO;
import model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.BusinessLogicException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeDAO employeeDAO;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach()
    public void initMockito() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach()
    public void createEmployee() {
        employee = createFakeEmployee();
    }

    @Test
    void testGetEmployeeByIdSuccess() {
        when(employeeDAO.get(employee.id()))
                .thenReturn(Optional.of(employee));

        GetEmployeeResponse result = employeeService.get(employee.id());

        verify(employeeDAO).get(employee.id());
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    void testGetEmployeeByIdNotFoundShouldThrow() {
        when(employeeDAO.get(employee.id()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.get(employee.id()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("Работника в таким id = %s нет в базе данных".formatted(employee.id()));

        verify(employeeDAO).get(employee.id());
    }

    @Test
    void testGetAllEmployeesSuccess() {
        when(employeeDAO.getAll()).thenReturn(List.of(employee));

        List<GetEmployeeResponse> employeeResponseList = employeeService.getAll();

        GetEmployeeResponse resultEmployee = employeeResponseList.stream()
                .findFirst()
                .orElse(null);

        verify(employeeDAO).getAll();

        assertEquals(employeeResponseList.size(), 1);
        assertThat(resultEmployee)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    void testSaveEmployeeSuccess() {
        CreateEmployeeRequest employeeRequest = createFakeEmployeeRequest();

        employeeService.save(employeeRequest);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeDAO).save(captor.capture());

        Employee employeeToBeSaved = captor.getValue();

        assertThat(employeeToBeSaved.department()).isEqualTo(employeeRequest.department());
        assertThat(employeeToBeSaved.position()).isEqualTo(employeeRequest.position());
    }

    @Test
    void testUpdateEmployeeSuccess() {
        when(employeeDAO.get(employee.id()))
                .thenReturn(Optional.of(employee));

        UpdateEmployeeRequest updateEmployeeRequest = new UpdateEmployeeRequest(
                "Developer",
                "IT",
                new Timestamp(System.currentTimeMillis()));

        employeeService.update(employee.id(), updateEmployeeRequest);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);

        verify(employeeDAO).update(eq(employee.id()), captor.capture());

        Employee employeeToBeUpdate = captor.getValue();

        assertThat(employeeToBeUpdate.position()).isEqualTo(updateEmployeeRequest.position());
        assertThat(employeeToBeUpdate.department()).isEqualTo(updateEmployeeRequest.department());
    }

    @Test
    void testUpdateEmployeeNotFoundShouldThrow() {
        when(employeeDAO.get(employee.id()))
                .thenReturn(Optional.empty());

        UpdateEmployeeRequest updateEmployeeRequest = new UpdateEmployeeRequest(
                "Developer",
                "IT",
                new Timestamp(System.currentTimeMillis()));

        assertThatThrownBy(() -> employeeService.update(employee.id(), updateEmployeeRequest))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("Работника в таким id = %s нет в базе данных".formatted(employee.id()));
    }

    @Test
    void toDeleteSuccess() {
        employeeService.delete(employee.id());

        verify(employeeDAO).delete(employee.id());
    }

    private Employee createFakeEmployee() {
        return new Employee(
                UUID.randomUUID(),
                "Security officer",
                "Security",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        );
    }

    private CreateEmployeeRequest createFakeEmployeeRequest() {
        return new CreateEmployeeRequest(
                "Manager",
                "Sales"
        );
    }
}