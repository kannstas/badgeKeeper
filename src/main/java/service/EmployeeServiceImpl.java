package service;

import api.request.employee.CreateEmployeeRequest;
import api.request.employee.UpdateEmployeeRequest;
import api.response.employee.GetEmployeeResponse;
import dao.EmployeeDAO;
import jakarta.inject.Inject;
import model.Employee;
import util.convert.ConvertEmployee;
import util.exception.IdNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;


public class EmployeeServiceImpl {
    @Inject
    private EmployeeDAO employeeDAO;
    Logger logger = Logger.getLogger(Employee.class.getName());

    public GetEmployeeResponse getById(UUID id) {
        requireNonNull(id);
        logger.info("get by id start: id = %s".formatted(id));

        return employeeDAO.get(id)
                .map(ConvertEmployee::toEmployee)
                .orElseThrow(() -> new IdNotFoundException("employee",id));
    }

    public List<GetEmployeeResponse> getAll() {
        logger.info("get all employees: start");
        return employeeDAO.getAll().stream()
                .map(ConvertEmployee::toEmployee)
                .collect(Collectors.toList());
    }

    public void save(CreateEmployeeRequest employeeRequest) {
        requireNonNull(employeeRequest);
        logger.info("save employee start: request = %s".formatted(employeeRequest));

        Employee employee = ConvertEmployee.toEmployee(employeeRequest);
        employeeDAO.save(employee);
    }

    public void update(UUID id, UpdateEmployeeRequest employeeRequest) {
        requireNonNull(id);
        requireNonNull(employeeRequest);
        logger.info("update employee start: id =%s request = %s".formatted(id,employeeRequest));

        GetEmployeeResponse employeeResponse = getById(id);
        Employee employee = ConvertEmployee.toEmployee(id, employeeResponse, employeeRequest);
        employeeDAO.update(id, employee);
    }

    public void delete(UUID id) {
        requireNonNull(id);
        logger.info("delete employee start: id = %s".formatted(id));

        employeeDAO.delete(id);
    }
}