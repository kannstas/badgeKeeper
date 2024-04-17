package util.generator.badge;

import model.Badge;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class BadgeGenerator {
    public static Badge generateBadge() {
        return new Badge(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                randomAlphanumeric(10),
                Instant.now().truncatedTo(ChronoUnit.MILLIS),
                Instant.now().truncatedTo(ChronoUnit.MILLIS),
                true
        );
    }

    public static Badge generateBadge(UUID recipientEmployeeId,
                                      UUID issuerEmployeeId) {
        return new Badge(
                UUID.randomUUID(),
                recipientEmployeeId,
                issuerEmployeeId,
                randomAlphanumeric(10),
                Instant.now().truncatedTo(ChronoUnit.MILLIS),
                Instant.now().truncatedTo(ChronoUnit.MILLIS),
                true
        );

    }
}
