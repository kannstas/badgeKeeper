package api.response.employee.personal.data;

import java.util.UUID;

public record GetEmployeePersonalDataResponse(UUID id,
                                              UUID employeeId,
                                              String firstName,
                                              String lastName,
                                              String middleName,
                                              Integer age,
                                              String address,
                                              String phoneNumber,
                                              String email){
}