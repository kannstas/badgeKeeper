package api.response.badge;

import java.time.Instant;
import java.util.UUID;

public record GetBadgeResponse (UUID id,
                                UUID recipientEmployeeId,
                                UUID issuerEmployeeId,
                                String badgeSerialNumber,
                                Instant issuanceDate,
                                Instant expirationDate,
                                Boolean active) {
}