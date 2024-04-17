package util.convert;

import api.request.employee.CreateEmployeeRequest;
import api.request.employee.UpdateEmployeeRequest;
import api.response.employee.GetEmployeeResponse;
import model.Employee;

import java.time.Instant;
import java.util.UUID;

public class ConvertEmployee {
    public static GetEmployeeResponse toEmployee(Employee employee) {
        return new GetEmployeeResponse(
                employee.id(),
                employee.position(),
                employee.department(),
                employee.createdAt(),
                employee.updatedAt()
        );
    }

    public static Employee toEmployee(CreateEmployeeRequest employeeRequest) {
        return new Employee(
                UUID.randomUUID(),
                employeeRequest.position(),
                employeeRequest.department(),
                Instant.now(),
                Instant.now()

        );
    }

    public static Employee toEmployee(UUID id,
                                      GetEmployeeResponse employeeResponse,
                                      UpdateEmployeeRequest employeeRequest) {
        return new Employee(
                id,
                employeeRequest.position(),
                employeeRequest.department(),
                employeeResponse.createdAt(),
                Instant.now()
        );
    }
}