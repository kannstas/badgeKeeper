package servlet.employeePersonalData;

import api.response.employeePersonalData.GetAllEmployeesPersonalDataResponse;
import api.response.employeePersonalData.GetEmployeePersonalDataResponse;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeePersonalDataServiceImpl;
import util.JsonUtils;
import java.io.IOException;
import java.util.List;

@WebServlet("/employeesPersonalData")
public class GetAllEmployeesPersonalData extends HttpServlet {

    @Inject
    EmployeePersonalDataServiceImpl employeePersonalDataService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List <GetEmployeePersonalDataResponse> personalDataList = employeePersonalDataService.getAll();
        GetAllEmployeesPersonalDataResponse allEmployeesPersonalData = new GetAllEmployeesPersonalDataResponse(personalDataList);

        resp.getWriter().write(JsonUtils.toJson(allEmployeesPersonalData));
    }
}