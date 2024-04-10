package api.response.badge;

import java.sql.Timestamp;
import java.util.UUID;

public record GetBadgeResponse (UUID id,
                                UUID recipientEmployeeId,
                                UUID issuerEmployeeId,
                                String badgeSerialNumber,
                                Timestamp issuanceDate,
                                Timestamp expirationDate,
                                Boolean active) {
}