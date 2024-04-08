package servlet.employeePersonalData;

import api.request.employeePersonalData.CreateEmployeePersonalDataRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeePersonalDataServiceImpl;
import java.io.IOException;

@WebServlet("employeePersonalData/save")
public class SaveEmployeePersonalDataServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Inject
    private EmployeePersonalDataServiceImpl personalDataService;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        CreateEmployeePersonalDataRequest createPersonalDataRequest = objectMapper.readValue(req.getInputStream(), CreateEmployeePersonalDataRequest.class);
        personalDataService.save(createPersonalDataRequest);
    }
}