package servlet.badge;

import api.response.badge.GetAllBadgesResponse;
import api.response.badge.GetBadgeResponse;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BadgeServiceImpl;
import util.json.JsonUtils;

import java.io.IOException;

import static util.servlet.ServletUtils.addDefaultHeaders;
import static util.servlet.ServletUtils.extractIdOrThrow;

@WebServlet("/badges")
public class GetBadgesServlet extends HttpServlet {
    @Inject
    BadgeServiceImpl badgeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("id") != null) {
            GetBadgeResponse badgeResponse = badgeService.getById(extractIdOrThrow(req, "id"));
            resp.getWriter().write(JsonUtils.toJson(badgeResponse));
            addDefaultHeaders(resp);
        } else {
            GetAllBadgesResponse allBadgesResponse = new GetAllBadgesResponse(badgeService.getAll());
            resp.getWriter().write(JsonUtils.toJson(allBadgesResponse));
            addDefaultHeaders(resp);

        }
    }
}