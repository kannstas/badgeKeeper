package api.request.badge;

import java.util.UUID;

public record CreateBadgeRequest(UUID recipientEmployeeId,
                                 UUID issuerEmployeeId,
                                 String badgeSerialNumber) {
}