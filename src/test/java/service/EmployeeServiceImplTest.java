package service;

import api.common.Position;
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
import util.exception.IdNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static util.generator.employee.EmployeeGenerator.generateEmployee;

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
        employee = generateEmployee();
    }

    @Test
    void testGetEmployeeByIdSuccess() {
        when(employeeDAO.get(employee.id()))
                .thenReturn(Optional.of(employee));

        GetEmployeeResponse result = employeeService.getById(employee.id());

        verify(employeeDAO).get(employee.id());
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    void testGetEmployeeByIdNotFoundShouldThrow() {
        when(employeeDAO.get(employee.id()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getById(employee.id()))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessageContaining("В базе данных нет employee с id=%s".formatted(employee.id()));

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

        assertEquals(1, employeeResponseList.size());
        assertThat(resultEmployee)
                .usingRecursiveComparison()
                .isEqualTo(employee);
    }

    @Test
    void testSaveEmployeeSuccess() {
        CreateEmployeeRequest employeeRequest = new CreateEmployeeRequest(
                Position.MANAGER,
                randomAlphanumeric(10)
        );

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
                Position.MANAGER,
                randomAlphanumeric(10));

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
                Position.MANAGER,
                randomAlphanumeric(10));

        assertThatThrownBy(() -> employeeService.update(employee.id(), updateEmployeeRequest))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessageContaining("В базе данных нет employee с id=%s".formatted(employee.id()));
    }

    @Test
    void testDeleteSuccess() {
        employeeService.delete(employee.id());

        verify(employeeDAO).delete(employee.id());
    }

}