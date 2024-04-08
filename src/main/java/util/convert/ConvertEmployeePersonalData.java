package util.convert;

import api.request.employeePersonalData.CreateEmployeePersonalDataRequest;
import api.request.employeePersonalData.UpdateEmployeePersonalDataRequest;
import api.response.employeePersonalData.GetEmployeePersonalDataResponse;
import model.EmployeePersonalData;
import java.util.UUID;

public class ConvertEmployeePersonalData {
    public static GetEmployeePersonalDataResponse employeePersonalDataToEmployeePersonalDataResponse (EmployeePersonalData personalData) {
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

    public static EmployeePersonalData employeePersonalDataRequestToEmployeePersonalData (CreateEmployeePersonalDataRequest personalDataRequest) {
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

    public static EmployeePersonalData updateEmployeePersonalDataRequestToEmployeePersonalData (UUID id, UpdateEmployeePersonalDataRequest personalDataRequest) {
        return new EmployeePersonalData(
                id,
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