package api.response.employee;

import java.util.List;

public record GetAllEmployeesResponse(List<GetEmployeeResponse> employees) {
}

