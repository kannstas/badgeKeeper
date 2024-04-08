package servlet.employee;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeeServiceImpl;
import java.util.UUID;

@WebServlet("/employees/delete")
public class DeleteEmployeeServlet extends HttpServlet {
    @Inject
    private EmployeeServiceImpl employeeService;

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)  {
        UUID id = UUID.fromString(req.getParameter("id"));
        employeeService.delete(id);
    }
}