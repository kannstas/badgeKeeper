package util.convert;

import api.request.employee.CreateEmployeeRequest;
import api.request.employee.UpdateEmployeeRequest;
import api.response.employee.GetEmployeeResponse;
import model.Employee;
import java.sql.Timestamp;
import java.util.UUID;

public class ConvertEmployee {
    public static GetEmployeeResponse employeeToEmployeeResponse(Employee employee) {
        return new GetEmployeeResponse(
                employee.id(),
                employee.position(),
                employee.department(),
                employee.createdAt(),
                employee.updatedAt()
        );
    }

    public static Employee employeeRequestToEmployee(CreateEmployeeRequest employeeRequest) {
        return new Employee(
                UUID.randomUUID(),
                employeeRequest.position(),
                employeeRequest.department(),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())

        );
    }
    public static Employee updateEmployeeRequestToEmployee(UUID id, UpdateEmployeeRequest employeeRequest) {
        return new Employee(
                id,
                employeeRequest.position(),
                employeeRequest.department(),
                employeeRequest.createdAt(),
                new Timestamp(System.currentTimeMillis())
        );
    }
}