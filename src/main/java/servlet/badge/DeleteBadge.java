package servlet.badge;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BadgeServiceImpl;
import java.util.UUID;

@WebServlet("/badge/delete")
public class DeleteBadge extends HttpServlet {
    @Inject
    private BadgeServiceImpl badgeService;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        UUID id = UUID.fromString(req.getParameter("id"));
        badgeService.delete(id);
    }
}