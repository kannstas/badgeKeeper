package api.request.employee.personal.data;

import java.util.UUID;

public record CreateEmployeePersonalDataRequest(UUID employeeId,
                                                String firstName,
                                                String lastName,
                                                String middleName,
                                                Integer age,
                                                String address,
                                                String phoneNumber,
                                                String email) {
}