package service;

import api.request.employee.CreateEmployeeRequest;
import api.request.employee.UpdateEmployeeRequest;
import api.response.employee.GetEmployeeResponse;
import dao.EmployeeDAO;
import jakarta.inject.Inject;
import model.Employee;
import util.convert.ConvertEmployee;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class EmployeeServiceImpl {
    @Inject
    private EmployeeDAO employeeDAO;

    public GetEmployeeResponse get(UUID id) {
        Employee employee = employeeDAO.get(id).orElse(null);
        if (employee == null) {
            throw new RuntimeException("Работника в таким ID нет в базе данных");
        }
        return ConvertEmployee.employeeToEmployeeResponse(employee);
    }

    public List<GetEmployeeResponse> getAll() {
        return employeeDAO.getAll().stream()
                .map(ConvertEmployee::employeeToEmployeeResponse)
                .collect(Collectors.toList());
    }

    public void save(CreateEmployeeRequest employeeRequest) {
        Employee employee = ConvertEmployee.employeeRequestToEmployee(employeeRequest);
        employeeDAO.save(employee);
    }

    public void update(UUID id, UpdateEmployeeRequest employeeRequest) {
        GetEmployeeResponse employeeResponse = get(id);
        if (employeeResponse != null) {
            Employee employee = ConvertEmployee.updateEmployeeRequestToEmployee(id, employeeRequest);
            employeeDAO.update(id, employee);
        }else {
            throw new RuntimeException("Невозможно обновить данные в таблице. В базе данных нет работника с таким ID");
        }
    }

    public void delete(UUID id) {
        employeeDAO.delete(id);
    }
}