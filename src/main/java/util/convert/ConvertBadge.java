package util.convert;

import api.request.badge.CreateBadgeRequest;
import api.response.badge.GetBadgeResponse;
import model.Badge;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

public class ConvertBadge {

    public static GetBadgeResponse badgeToBadgeResponse(Badge badge) {
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

    public static Badge badgeRequestToBadge(CreateBadgeRequest badgeRequest) {
        return new Badge(
                UUID.randomUUID(),
                badgeRequest.recipientEmployeeId(),
                badgeRequest.issuerEmployeeId(),
                badgeRequest.badgeSerialNumber(),
                new Timestamp(System.currentTimeMillis()),
                currentTimePlusYear(),
                true
        );
    }

   private static Timestamp currentTimePlusYear() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(currentTime.getTime());
        calendar.add(Calendar.YEAR, 1);
        return new Timestamp(calendar.getTimeInMillis());
    }
}