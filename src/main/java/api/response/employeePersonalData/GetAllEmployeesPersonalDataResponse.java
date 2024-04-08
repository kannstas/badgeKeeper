package api.response.employeePersonalData;

import java.util.List;

public record GetAllEmployeesPersonalDataResponse(List<GetEmployeePersonalDataResponse> employeesPersonalData) {
}