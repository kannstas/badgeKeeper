package model;

import java.util.UUID;

public record EmployeePersonalData (UUID id,
                                    UUID employeeId,
                                    String firstName,
                                    String lastName,
                                    String middleName,
                                    Integer age,
                                    String address,
                                    String phoneNumber,
                                    String email) {
}