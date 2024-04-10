package api.response.badge;

import java.util.List;

public record GetAllBadgesResponse(List<GetBadgeResponse> badges) {
}