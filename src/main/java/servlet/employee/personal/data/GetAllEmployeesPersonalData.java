package servlet.employee.personal.data;

import api.response.employee.personal.data.GetAllEmployeesPersonalDataResponse;
import api.response.employee.personal.data.GetEmployeePersonalDataResponse;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeePersonalDataServiceImpl;
import util.json.JsonUtils;
import util.servlet.ServletUtils;

import java.io.IOException;
import java.util.List;

import static util.servlet.ServletUtils.addDefaultHeaders;

@WebServlet("/employeesPersonalData")
public class GetAllEmployeesPersonalData extends HttpServlet {

    @Inject
    EmployeePersonalDataServiceImpl employeePersonalDataService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("id") != null) {
            GetEmployeePersonalDataResponse personalDataResponse =
                    employeePersonalDataService.getByEmployeeId(ServletUtils.extractIdOrThrow(req, "id"));
            resp.getWriter().write(JsonUtils.toJson(personalDataResponse));
            addDefaultHeaders(resp);

        } else {
            List<GetEmployeePersonalDataResponse> personalDataList = employeePersonalDataService.getAll();
            GetAllEmployeesPersonalDataResponse allEmployeesPersonalData = new GetAllEmployeesPersonalDataResponse(personalDataList);
            resp.getWriter().write(JsonUtils.toJson(allEmployeesPersonalData));
            addDefaultHeaders(resp);
        }
    }
}