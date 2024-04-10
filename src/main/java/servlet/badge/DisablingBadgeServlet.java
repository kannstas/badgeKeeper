package servlet.badge;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BadgeServiceImpl;
import java.util.UUID;

@WebServlet("/badge/disable")
public class DisablingBadgeServlet extends HttpServlet {
    @Inject
    private BadgeServiceImpl badgeService;
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        UUID id = UUID.fromString(req.getParameter("id"));
        badgeService.disable(id);
    }
}