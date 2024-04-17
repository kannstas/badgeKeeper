package servlet.badge;

import api.request.badge.CreateBadgeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.BadgeServiceImpl;

import java.io.IOException;

@WebServlet("/badges/issue")
public class IssueBadgeServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Inject
    private BadgeServiceImpl badgeService;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CreateBadgeRequest badgeRequest = objectMapper.readValue(req.getInputStream(), CreateBadgeRequest.class);
        badgeService.save(badgeRequest);
    }
}