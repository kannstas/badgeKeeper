package util.generator.employeePersonalData;

import model.EmployeePersonalData;

import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class EmployeePersonalDataGenerator {
    public static EmployeePersonalData generateEmployeePersonalData() {
        return new EmployeePersonalData(
                UUID.randomUUID(),
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

    public static EmployeePersonalData generateEmployeePersonalData(UUID employeeId) {
        return new EmployeePersonalData(
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