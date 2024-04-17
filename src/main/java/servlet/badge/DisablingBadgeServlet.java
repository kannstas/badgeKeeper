package servlet.badge;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BadgeServiceImpl;
import util.servlet.ServletUtils;

@WebServlet("/badges/disable")
public class DisablingBadgeServlet extends HttpServlet {

    @Inject
    private BadgeServiceImpl badgeService;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        badgeService.disable(ServletUtils.extractIdOrThrow(req,"id"));
    }
}