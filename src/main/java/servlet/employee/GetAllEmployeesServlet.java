package servlet.employee;

import api.response.employee.GetAllEmployeesResponse;
import api.response.employee.GetEmployeeResponse;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeeServiceImpl;
import util.JsonUtils;
import java.io.IOException;
import java.util.List;

@WebServlet("/employees")
public class GetAllEmployeesServlet extends HttpServlet {

    @Inject
    private EmployeeServiceImpl employeeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<GetEmployeeResponse> employees = employeeService.getAll();
        GetAllEmployeesResponse allEmployeesResponse = new GetAllEmployeesResponse(employees);
        resp.getWriter().write(JsonUtils.toJson(allEmployeesResponse));
    }
}
