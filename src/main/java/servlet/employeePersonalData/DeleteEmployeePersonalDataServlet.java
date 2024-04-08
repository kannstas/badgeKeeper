package servlet.employeePersonalData;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmployeePersonalDataServiceImpl;
import java.util.UUID;
@WebServlet("/employeePersonalData/delete")
public class DeleteEmployeePersonalDataServlet extends HttpServlet {
    @Inject
    EmployeePersonalDataServiceImpl employeePersonalDataService;
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)  {
        UUID id = UUID.fromString(req.getParameter("id"));
        employeePersonalDataService.delete(id);
    }
}