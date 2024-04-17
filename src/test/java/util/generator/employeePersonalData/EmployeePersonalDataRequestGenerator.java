package util.generator.employeePersonalData;

import api.request.employee.personal.data.CreateEmployeePersonalDataRequest;

import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class EmployeePersonalDataRequestGenerator {
    public static CreateEmployeePersonalDataRequest generatePersonalDataRequest() {
        return new CreateEmployeePersonalDataRequest(
                UUID.randomUUID(),
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                20,
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                randomAlphanumeric(10)
        );
    }
}