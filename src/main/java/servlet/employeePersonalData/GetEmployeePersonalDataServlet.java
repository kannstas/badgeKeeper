package servlet.employeePersonalData;

import api.response.employeePersonalData.GetEmployeePersonalDataResponse;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeePersonalDataServiceImpl;
import util.JsonUtils;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/employeePersonalData")
public class GetEmployeePersonalDataServlet extends HttpServlet {
    @Inject
    EmployeePersonalDataServiceImpl employeePersonalDataService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        UUID employeeId = UUID.fromString(req.getParameter("id"));
        GetEmployeePersonalDataResponse personalDataResponse = employeePersonalDataService.get(employeeId);

        resp.getWriter().write(JsonUtils.toJson(personalDataResponse));
    }

}