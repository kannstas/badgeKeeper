package util.convert;

import api.request.badge.CreateBadgeRequest;
import api.response.badge.GetBadgeResponse;
import model.Badge;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ConvertBadgeTest {
    @Test
    void badgeToBadgeResponse() {

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(currentTime.getTime());
        calendar.add(Calendar.YEAR, 1);
        Timestamp plusYear = new Timestamp(calendar.getTimeInMillis());

        Badge badge = new Badge(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "badgeSerialNumber",
                new Timestamp(System.currentTimeMillis()),
                plusYear,
                true
        );

        GetBadgeResponse badgeResponse = ConvertBadge.badgeToBadgeResponse(badge);

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
        Badge badge = ConvertBadge.badgeRequestToBadge(badgeRequest);

        assertEquals(badgeRequest.recipientEmployeeId(), badge.recipientEmployeeId());
        assertEquals(badgeRequest.issuerEmployeeId(), badge.issuerEmployeeId());
        assertEquals(badgeRequest.badgeSerialNumber(), badge.badgeSerialNumber());

    }
}
