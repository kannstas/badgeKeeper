package servlet.employee.personal.data;

import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeePersonalDataServiceImpl;
import util.servlet.ServletUtils;

@WebServlet("/employeesPersonalData/delete")
public class DeleteEmployeePersonalDataServlet extends HttpServlet {
    @Inject
    EmployeePersonalDataServiceImpl employeePersonalDataService;
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)  {
        employeePersonalDataService.delete(ServletUtils.extractIdOrThrow(req, "id"));
    }
}