package util.generator.employee;

import api.common.Position;
import model.Employee;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class EmployeeGenerator {

    public static Employee generateEmployee() {
        return generateEmployee(
                UUID.randomUUID(),
                Position.MANAGER,
                randomAlphanumeric(10),
                Instant.now().truncatedTo(ChronoUnit.MILLIS),
                Instant.now().truncatedTo(ChronoUnit.MILLIS)
        );
    }

    public static Employee generateEmployee(
            Position position,
            String department
    ) {
        return generateEmployee(
                UUID.randomUUID(),
                position,
                department,
                Instant.now().truncatedTo(ChronoUnit.MILLIS),
                Instant.now().truncatedTo(ChronoUnit.MILLIS)
        );
    }

    public static Employee generateEmployee(
            UUID id,
            Position position,
            String department,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Employee(
                id,
                position,
                department,
                createdAt,
                updatedAt
        );
    }
}