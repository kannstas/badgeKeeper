package servlet.employee;

import api.response.employee.GetEmployeeResponse;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import service.EmployeeServiceImpl;
import util.JsonUtils;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/employee")
public class GetEmployeeServlet extends HttpServlet {
    @Inject
    EmployeeServiceImpl employeeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");

        if (StringUtils.isNotBlank(idParam)) {

            UUID id = UUID.fromString(idParam);
            GetEmployeeResponse employee = employeeService.get(id);

            resp.getWriter().write(JsonUtils.toJson(employee));
        }
    }
}