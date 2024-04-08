package api.response.employeePersonalData;

import java.util.UUID;

public record GetEmployeePersonalDataResponse(UUID id,
                                              UUID employeeId,
                                              String firstName,
                                              String lastName,
                                              String middleName,
                                              int age,
                                              String address,
                                              String phoneNumber,
                                              String email){
}