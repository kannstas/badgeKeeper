package servlet.employee;

import api.response.employee.GetAllEmployeesResponse;
import api.response.employee.GetEmployeeResponse;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeeServiceImpl;
import util.json.JsonUtils;

import java.io.IOException;
import java.util.List;

import static util.servlet.ServletUtils.addDefaultHeaders;
import static util.servlet.ServletUtils.extractIdOrThrow;

@WebServlet("/employees")
public class GetEmployeesServlet extends HttpServlet {

    @Inject
    private EmployeeServiceImpl employeeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (req.getParameter("id") != null) {
            GetEmployeeResponse employee = employeeService.getById(extractIdOrThrow(req, "id"));
            resp.getWriter().write(JsonUtils.toJson(employee));
            addDefaultHeaders(resp);

        } else {
            List<GetEmployeeResponse> employees = employeeService.getAll();
            GetAllEmployeesResponse allEmployeesResponse = new GetAllEmployeesResponse(employees);
            resp.getWriter().write(JsonUtils.toJson(allEmployeesResponse));
            addDefaultHeaders(resp);
        }
    }
}
