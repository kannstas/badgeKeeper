package service;

import api.common.Position;
import api.request.employee.personal.data.CreateEmployeePersonalDataRequest;
import api.request.employee.personal.data.UpdateEmployeePersonalDataRequest;
import api.response.employee.GetEmployeeResponse;
import api.response.employee.personal.data.GetEmployeePersonalDataResponse;
import dao.EmployeePersonalDataDAO;
import model.Employee;
import model.EmployeePersonalData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.exception.BusinessLogicException;
import util.exception.IdNotFoundException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static util.generator.employee.EmployeeGenerator.generateEmployee;
import static util.generator.employeePersonalData.EmployeePersonalDataGenerator.generateEmployeePersonalData;
import static util.generator.employeePersonalData.EmployeePersonalDataRequestGenerator.generatePersonalDataRequest;
class EmployeePersonalDataServiceImplTest {

    @Mock
    private EmployeePersonalDataDAO employeePersonalDataDAO;
    @Mock
    private EmployeeServiceImpl employeeService;
    @InjectMocks
    private EmployeePersonalDataServiceImpl employeePersonalDataService;

    private Employee employee;
    private EmployeePersonalData personalData;

    @BeforeEach()
    public void initMockito() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        employee = generateEmployee();
        personalData = generateEmployeePersonalData();
    }

    @Test
    void testGetPersonalDataByEmployeeIdSuccess() {

        when(employeePersonalDataDAO.get(employee.id()))
                .thenReturn(Optional.of(personalData));

        GetEmployeePersonalDataResponse result = employeePersonalDataService.getByEmployeeId(employee.id());

        verify(employeePersonalDataDAO).get(employee.id());

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(personalData);
    }

    @Test
    void testGetPersonalDataByEmployeeIdNotFoundThrowException() {
        when(employeePersonalDataDAO.get(employee.id()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeePersonalDataService.getByEmployeeId(employee.id()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("В базе данных нет employeePersonalData with employee с id=%s".formatted(employee.id()));
    }

    @Test
    void getAllEmployeePersonalDataSuccess() {
        when(employeePersonalDataDAO.getAll())
                .thenReturn(List.of(personalData));

        List<GetEmployeePersonalDataResponse> employeePersonalDataResponseList = employeePersonalDataService.getAll();

        GetEmployeePersonalDataResponse resultPersonalData = employeePersonalDataResponseList.stream()
                .findFirst()
                .orElseThrow();

        verify(employeePersonalDataDAO).getAll();

        assertEquals(1, employeePersonalDataResponseList.size());
        assertThat(resultPersonalData)
                .usingRecursiveComparison()
                .isEqualTo(personalData);
    }

    @Test
    void saveEmployeePersonalDataSuccess() {
        CreateEmployeePersonalDataRequest request = generatePersonalDataRequest();

        when(employeeService.getById(request.employeeId()))
                .thenReturn(
                        new GetEmployeeResponse
                                (
                                        UUID.randomUUID(),
                                        Position.MANAGER,
                                        randomAlphanumeric(10),
                                        Instant.now().truncatedTo(ChronoUnit.MILLIS),
                                        Instant.now().truncatedTo(ChronoUnit.MILLIS)
                                )
                );

        employeePersonalDataService.save(request);

        ArgumentCaptor<EmployeePersonalData> captor = ArgumentCaptor.forClass(EmployeePersonalData.class);

        verify(employeePersonalDataDAO).save(captor.capture());

        EmployeePersonalData resultPersonalData = captor.getValue();

        assertThat(resultPersonalData.firstName()).isEqualTo(request.firstName());
        assertThat(resultPersonalData.lastName()).isEqualTo(request.lastName());
        assertThat(resultPersonalData.middleName()).isEqualTo(request.middleName());
        assertThat(resultPersonalData.age()).isEqualTo(request.age());
        assertThat(resultPersonalData.address()).isEqualTo(request.address());
        assertThat(resultPersonalData.phoneNumber()).isEqualTo(request.phoneNumber());
        assertThat(resultPersonalData.email()).isEqualTo(request.email());
    }

    @Test
    void saveEmployeePersonalDataNotFoundEmployeeShouldThrow() {
        CreateEmployeePersonalDataRequest request = generatePersonalDataRequest();

        when(employeeService.getById(request.employeeId()))
                .thenThrow(new IdNotFoundException("employee", request.employeeId()));

        assertThatThrownBy(() -> employeePersonalDataService.save(request))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessageContaining("В базе данных нет employee с id=%s".formatted(request.employeeId()));
    }

    @Test
    void updateEmployeePersonalDataSuccess() {

        UpdateEmployeePersonalDataRequest updateRequest = new UpdateEmployeePersonalDataRequest(
                UUID.randomUUID(),
                employee.id(),
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                20,
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                randomAlphanumeric(10)
        );

        when(employeePersonalDataDAO.get(updateRequest.employeeId()))
                .thenReturn(Optional.of(generateEmployeePersonalData()));

        employeePersonalDataService.update(updateRequest);

        ArgumentCaptor<EmployeePersonalData> captor = ArgumentCaptor.forClass(EmployeePersonalData.class);
        verify(employeePersonalDataDAO).update(captor.capture());

        EmployeePersonalData updatedPersonalData = captor.getValue();

        assertThat(updateRequest)
                .usingRecursiveComparison()
                .isEqualTo(updatedPersonalData);

    }

    @Test
    void updateEmployeePersonalDataNotFoundEmployeeShouldThrow(){
        UpdateEmployeePersonalDataRequest updateRequest = new UpdateEmployeePersonalDataRequest(
                UUID.randomUUID(),
                employee.id(),
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                20,
                randomAlphanumeric(10),
                randomAlphanumeric(10),
                randomAlphanumeric(10)
        );
        when(employeePersonalDataDAO.get(employee.id()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeePersonalDataService.update(updateRequest))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("В базе данных нет employeePersonalData with employee с id=%s".formatted(updateRequest.employeeId()));
    }

    @Test
    void testDeleteSuccess() {
        employeePersonalDataService.delete(personalData.id());

        verify(employeePersonalDataDAO).delete(personalData.id());
    }

}