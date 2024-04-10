package servlet.badge;

import api.response.badge.GetBadgeResponse;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BadgeServiceImpl;
import util.JsonUtils;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/badge")
public class GetBadgeServlet extends HttpServlet {
    @Inject
    BadgeServiceImpl badgeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID id = UUID.fromString(req.getParameter("id"));
        GetBadgeResponse badgeResponse = badgeService.getById(id);
        resp.getWriter().write(JsonUtils.toJson(badgeResponse));
    }
}