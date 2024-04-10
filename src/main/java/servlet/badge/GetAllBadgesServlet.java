package servlet.badge;

import api.response.badge.GetAllBadgesResponse;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BadgeServiceImpl;
import util.JsonUtils;
import java.io.IOException;

@WebServlet("/badges")
public class GetAllBadgesServlet extends HttpServlet {
    @Inject
    BadgeServiceImpl badgeService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GetAllBadgesResponse allBadgesResponse = new GetAllBadgesResponse(badgeService.getAll());
        resp.getWriter().write(JsonUtils.toJson(allBadgesResponse));
    }

}