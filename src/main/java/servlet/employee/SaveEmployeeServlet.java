package servlet.employee;

import api.request.employee.CreateEmployeeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeeServiceImpl;

import java.io.IOException;

@WebServlet("/employees/save")
public class SaveEmployeeServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Inject
    private EmployeeServiceImpl employeeService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CreateEmployeeRequest employee = objectMapper.readValue(req.getInputStream(), CreateEmployeeRequest.class);
        employeeService.save(employee);
    }
}