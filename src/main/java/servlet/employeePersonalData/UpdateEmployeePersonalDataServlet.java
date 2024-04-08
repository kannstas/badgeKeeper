package servlet.employeePersonalData;

import api.request.employeePersonalData.UpdateEmployeePersonalDataRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeePersonalDataServiceImpl;
import java.io.IOException;
import java.util.UUID;


@WebServlet("/employeePersonalData/update")
public class UpdateEmployeePersonalDataServlet extends HttpServlet {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Inject
    EmployeePersonalDataServiceImpl employeePersonalDataService;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UUID id = UUID.fromString(req.getParameter("id"));

        UpdateEmployeePersonalDataRequest updateRequest = objectMapper.readValue(req.getInputStream(), UpdateEmployeePersonalDataRequest.class);

        employeePersonalDataService.update(id, updateRequest);
    }
}