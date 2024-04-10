package model;

import java.sql.Timestamp;
import java.util.UUID;

public record Badge(UUID id,
                    UUID recipientEmployeeId,
                    UUID issuerEmployeeId,
                    String badgeSerialNumber,
                    Timestamp issuanceDate,
                    Timestamp expirationDate,
                    Boolean active ) {
}