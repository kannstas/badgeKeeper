package util.convert;

import api.request.badge.CreateBadgeRequest;
import api.response.badge.GetBadgeResponse;
import model.Badge;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ConvertBadge {

    public static GetBadgeResponse toBadge(Badge badge) {
        return new GetBadgeResponse(
                badge.id(),
                badge.recipientEmployeeId(),
                badge.issuerEmployeeId(),
                badge.badgeSerialNumber(),
                badge.issuanceDate(),
                badge.expirationDate(),
                badge.active()
        );
    }

    public static Badge toBadge(CreateBadgeRequest badgeRequest) {
        return new Badge(
                UUID.randomUUID(),
                badgeRequest.recipientEmployeeId(),
                badgeRequest.issuerEmployeeId(),
                badgeRequest.badgeSerialNumber(),
                Instant.now(),
                Instant.now().plus(365, ChronoUnit.DAYS),
                true
        );
    }
}