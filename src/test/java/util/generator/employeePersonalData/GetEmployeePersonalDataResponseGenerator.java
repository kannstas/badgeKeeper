package util.generator.employeePersonalData;

import api.response.employee.personal.data.GetEmployeePersonalDataResponse;

import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class GetEmployeePersonalDataResponseGenerator {
    public static GetEmployeePersonalDataResponse generatePersonalDataResponse(UUID employeeId) {
        return new GetEmployeePersonalDataResponse(
                UUID.randomUUID(),
                employeeId,
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