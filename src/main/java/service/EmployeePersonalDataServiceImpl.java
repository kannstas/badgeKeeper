package service;

import api.request.employee.personal.data.CreateEmployeePersonalDataRequest;
import api.request.employee.personal.data.UpdateEmployeePersonalDataRequest;
import api.response.employee.personal.data.GetEmployeePersonalDataResponse;
import dao.EmployeePersonalDataDAO;
import jakarta.inject.Inject;
import model.EmployeePersonalData;
import util.convert.ConvertEmployeePersonalData;
import util.exception.IdNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class EmployeePersonalDataServiceImpl {

    @Inject
    private EmployeePersonalDataDAO employeePersonalDataDAO;
    @Inject
    private EmployeeServiceImpl employeeService;
    Logger logger = Logger.getLogger(EmployeePersonalDataServiceImpl.class.getName());

    public GetEmployeePersonalDataResponse getByEmployeeId(UUID employeeId) {
        requireNonNull(employeeId);
        logger.info("get by employeeId: start: id = %s".formatted(employeeId));

        EmployeePersonalData employeePersonalData = employeePersonalDataDAO.get(employeeId).orElse(null);
        if (employeePersonalData == null) {
            throw new IdNotFoundException("employeePersonalData with employee", employeeId);
        }
        return ConvertEmployeePersonalData.toEmployeePersonalData(employeePersonalData);
    }

    public List<GetEmployeePersonalDataResponse> getAll() {
        logger.info("get all personal data start");

        return employeePersonalDataDAO.getAll().stream()
                .map(ConvertEmployeePersonalData::toEmployeePersonalData)
                .collect(Collectors.toList());
    }

    public void save(CreateEmployeePersonalDataRequest request) {
        requireNonNull(request);
        logger.info("save employeePersonalData: start: request = %s".formatted(request));

        employeeService.getById(request.employeeId());

        EmployeePersonalData personalData = ConvertEmployeePersonalData.toEmployeePersonalData(request);
        employeePersonalDataDAO.save(personalData);
    }

    public void update(UpdateEmployeePersonalDataRequest request) {
        requireNonNull(request);
        logger.info("update employeePersonalData: start: request = %s".formatted(request));

        getByEmployeeId(request.employeeId());
        EmployeePersonalData personalData = ConvertEmployeePersonalData.toEmployeePersonalData(request);
        employeePersonalDataDAO.update(personalData);

    }

    public void delete(UUID id) {
        requireNonNull(id);
        logger.info("get by employeeId: start: id = %s".formatted(id));

        employeePersonalDataDAO.delete(id);
    }
}