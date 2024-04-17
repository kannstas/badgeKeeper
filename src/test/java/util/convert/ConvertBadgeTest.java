package util.convert;

import api.request.badge.CreateBadgeRequest;
import api.response.badge.GetBadgeResponse;
import model.Badge;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Calendar;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConvertBadgeTest {
    @Test
    void badgeToBadgeResponse() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Instant plusYear = calendar.toInstant();

        Badge badge = new Badge(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "badgeSerialNumber",
                Instant.now(),
                plusYear,
                true
        );

        GetBadgeResponse badgeResponse = ConvertBadge.toBadge(badge);

        assertEquals(badge.id(), badgeResponse.id());
        assertEquals(badge.recipientEmployeeId(), badgeResponse.recipientEmployeeId());
        assertEquals(badge.issuerEmployeeId(), badgeResponse.issuerEmployeeId());
        assertEquals(badge.badgeSerialNumber(), badgeResponse.badgeSerialNumber());
        assertEquals(badge.issuanceDate(), badgeResponse.issuanceDate());
        assertEquals(badge.expirationDate(), badgeResponse.expirationDate());
        assertEquals(badge.active(), badgeResponse.active());
    }

    @Test
    void badgeRequestToBadge() {
        CreateBadgeRequest badgeRequest = new CreateBadgeRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "badgeSerialNumber"
        );
        Badge badge = ConvertBadge.toBadge(badgeRequest);

        assertEquals(badgeRequest.recipientEmployeeId(), badge.recipientEmployeeId());
        assertEquals(badgeRequest.issuerEmployeeId(), badge.issuerEmployeeId());
        assertEquals(badgeRequest.badgeSerialNumber(), badge.badgeSerialNumber());

    }
}
