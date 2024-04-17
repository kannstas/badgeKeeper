package servlet.employee.personal.data;

import api.request.employee.personal.data.UpdateEmployeePersonalDataRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeePersonalDataServiceImpl;

import java.io.IOException;


@WebServlet("/employeesPersonalData/update")
public class UpdateEmployeePersonalDataServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Inject
    EmployeePersonalDataServiceImpl employeePersonalDataService;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        UpdateEmployeePersonalDataRequest updateRequest = objectMapper.readValue(req.getInputStream(), UpdateEmployeePersonalDataRequest.class);

        employeePersonalDataService.update(updateRequest);
    }
}