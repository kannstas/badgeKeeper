package api.response.employee.personal.data;

import java.util.List;

public record GetAllEmployeesPersonalDataResponse(List<GetEmployeePersonalDataResponse> employeesPersonalData) {
}