package api.request.employeePersonalData;

import java.util.UUID;

public record CreateEmployeePersonalDataRequest (UUID employeeId,
                                                 String firstName,
                                                 String lastName,
                                                 String middleName,
                                                 int age,
                                                 String address,
                                                 String phoneNumber,
                                                 String email) {
}