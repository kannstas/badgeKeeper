package service;

import api.request.employeePersonalData.CreateEmployeePersonalDataRequest;
import api.request.employeePersonalData.UpdateEmployeePersonalDataRequest;
import api.response.employee.GetEmployeeResponse;
import api.response.employeePersonalData.GetEmployeePersonalDataResponse;
import dao.EmployeePersonalDataDAO;
import jakarta.inject.Inject;
import model.EmployeePersonalData;
import util.convert.ConvertEmployeePersonalData;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmployeePersonalDataServiceImpl {

    @Inject
    private EmployeePersonalDataDAO employeePersonalDataDAO;
    @Inject
    private EmployeeServiceImpl employeeService;

    public GetEmployeePersonalDataResponse get(UUID employeeId) {
        EmployeePersonalData employeePersonalData = employeePersonalDataDAO.get(employeeId).orElse(null);
        if (employeePersonalData== null) {
            throw new RuntimeException("В базе данных нет записи с таким ID");
        }
        return ConvertEmployeePersonalData.employeePersonalDataToEmployeePersonalDataResponse(employeePersonalData);
    }

    public GetEmployeePersonalDataResponse getEmployeePersonalData(UUID id) {
        EmployeePersonalData employeePersonalData = employeePersonalDataDAO.getEmployeePersonalData(id).orElse(null);
        if (employeePersonalData== null) {
            throw new RuntimeException("В базе данных нет записи с таким ID");
        }
        return ConvertEmployeePersonalData.employeePersonalDataToEmployeePersonalDataResponse(employeePersonalData);
    }
    public List<GetEmployeePersonalDataResponse> getAll() {
        return employeePersonalDataDAO.getAll().stream()
                .map(ConvertEmployeePersonalData::employeePersonalDataToEmployeePersonalDataResponse)
                .collect(Collectors.toList());
    }

    public void save(CreateEmployeePersonalDataRequest request) {
        GetEmployeeResponse employeeResponse = employeeService.get(request.employeeId());

        if(employeeResponse!=null) {
            EmployeePersonalData personalData = ConvertEmployeePersonalData.employeePersonalDataRequestToEmployeePersonalData(request);
            employeePersonalDataDAO.save(personalData);
        } else {
            throw new RuntimeException("В базе данных нет работника с таким ID");
        }
    }

    public void update(UUID id, UpdateEmployeePersonalDataRequest request) {

        GetEmployeePersonalDataResponse personalDataResponse = getEmployeePersonalData(id);

        if (personalDataResponse!=null) {
            EmployeePersonalData  personalData = ConvertEmployeePersonalData.updateEmployeePersonalDataRequestToEmployeePersonalData(id,request);
            employeePersonalDataDAO.update(personalData);
        } else {
            throw new RuntimeException("В базе данных нет персональных данных с таким ID");
        }
    }

    public void delete(UUID id) {
        employeePersonalDataDAO.delete(id);
    }
}