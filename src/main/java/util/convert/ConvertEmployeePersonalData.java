package util.convert;

import api.request.employee.personal.data.CreateEmployeePersonalDataRequest;
import api.request.employee.personal.data.UpdateEmployeePersonalDataRequest;
import api.response.employee.personal.data.GetEmployeePersonalDataResponse;
import model.EmployeePersonalData;

import java.util.UUID;

public class ConvertEmployeePersonalData {
    public static GetEmployeePersonalDataResponse toEmployeePersonalData(
            EmployeePersonalData personalData
    ) {
        return new GetEmployeePersonalDataResponse(
                personalData.id(),
                personalData.employeeId(),
                personalData.firstName(),
                personalData.lastName(),
                personalData.middleName(),
                personalData.age(),
                personalData.address(),
                personalData.phoneNumber(),
                personalData.email()
        );
    }

    public static EmployeePersonalData toEmployeePersonalData(
            CreateEmployeePersonalDataRequest personalDataRequest
    ) {
        return new EmployeePersonalData(
                UUID.randomUUID(),
                personalDataRequest.employeeId(),
                personalDataRequest.firstName(),
                personalDataRequest.lastName(),
                personalDataRequest.middleName(),
                personalDataRequest.age(),
                personalDataRequest.address(),
                personalDataRequest.phoneNumber(),
                personalDataRequest.email()
        );
    }

    public static EmployeePersonalData toEmployeePersonalData(
            UpdateEmployeePersonalDataRequest personalDataRequest
    ) {
        return new EmployeePersonalData(
                personalDataRequest.id(),
                personalDataRequest.employeeId(),
                personalDataRequest.firstName(),
                personalDataRequest.lastName(),
                personalDataRequest.middleName(),
                personalDataRequest.age(),
                personalDataRequest.address(),
                personalDataRequest.phoneNumber(),
                personalDataRequest.email()
        );
    }
}