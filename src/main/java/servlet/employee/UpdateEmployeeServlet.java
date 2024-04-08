package servlet.employee;

import api.request.employee.UpdateEmployeeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeeServiceImpl;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/employees/update")
public class UpdateEmployeeServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    private EmployeeServiceImpl employeeService;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UUID id = UUID.fromString(req.getParameter("id"));
        UpdateEmployeeRequest employee = objectMapper.readValue(req.getInputStream(), UpdateEmployeeRequest.class);
        employeeService.update(id, employee);
    }
}
