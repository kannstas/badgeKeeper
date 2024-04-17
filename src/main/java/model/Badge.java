package model;

import java.time.Instant;
import java.util.UUID;

public record Badge(UUID id,
                    UUID recipientEmployeeId,
                    UUID issuerEmployeeId,
                    String badgeSerialNumber,
                    Instant issuanceDate,
                    Instant expirationDate,
                    Boolean active ) {
}