package service;

import api.common.Position;
import api.request.badge.CreateBadgeRequest;
import api.response.badge.GetBadgeResponse;
import api.response.employee.GetEmployeeResponse;
import dao.BadgeDAO;
import dao.EmployeeDAO;
import model.Badge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.exception.BusinessLogicException;
import util.exception.IdNotFoundException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static util.generator.badge.BadgeGenerator.generateBadge;

class BadgeServiceImplUnitTest {
    @Mock
    private BadgeDAO badgeDAO;

    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    EmployeeDAO employeeDAO;
    @InjectMocks
    private BadgeServiceImpl badgeService;
    private Badge badge;

    @BeforeEach()
    public void initMockito() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        badge = generateBadge();
    }

    @Test
    void testGetBadgeByIdSuccess() {
        // given
        when(badgeDAO.get(badge.id()))
                .thenReturn(Optional.of(badge));

        // when
        GetBadgeResponse result = badgeService.getById(badge.id());

        // then
        verify(badgeDAO).get(badge.id());
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(badge);
    }

    @Test
    void testGetBadgeByIdNotFoundShouldThrow() {

        when(badgeDAO.get(badge.id()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> badgeService.getById(badge.id()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("В базе данных нет badge с id=%s".formatted(badge.id()));

        verify(badgeDAO).get(badge.id());
    }

    @Test
    void testGetAllBadgesSuccess() {

        when(badgeDAO.getAll()).thenReturn(List.of(badge));

        List<GetBadgeResponse> badgeresponseList = badgeService.getAll();
        GetBadgeResponse resultBadge = badgeresponseList.stream()
                .findFirst()
                .orElse(null);

        verify(badgeDAO).getAll();

        assertEquals(badgeresponseList.size(), 1);
        assertThat(resultBadge)
                .usingRecursiveComparison()
                .isEqualTo(badge);
    }

    @Test
    void testSaveBadgeSuccess() {
        //given
        GetEmployeeResponse recipientEmployeeResponse = generateEmployeeResponse();
        GetEmployeeResponse issueEmployeeResponse = generateEmployeeResponse();

        CreateBadgeRequest createBadgeRequest = new CreateBadgeRequest(
                recipientEmployeeResponse.id(),
                issueEmployeeResponse.id(),
                "serial_number"
        );

        when(employeeService.getById(any()))
                .thenReturn(issueEmployeeResponse, recipientEmployeeResponse);

        //when
        badgeService.save(createBadgeRequest);

        ArgumentCaptor<Badge> captor = ArgumentCaptor.forClass(Badge.class);
        verify(badgeDAO).save(captor.capture());

        Badge badgeToBeSaved = captor.getValue();

        //then
        assertThat(badgeToBeSaved.issuerEmployeeId()).isEqualTo(issueEmployeeResponse.id());
        assertThat(badgeToBeSaved.recipientEmployeeId()).isEqualTo(recipientEmployeeResponse.id());
    }

    @Test
    void testSaveBadgeWithNoneExistentRecipientEmployeeShouldThrow() {

        CreateBadgeRequest createBadgeRequest = generateRequest();

        when(employeeService.getById(createBadgeRequest.recipientEmployeeId()))
                .thenThrow(new IdNotFoundException("employee", createBadgeRequest.recipientEmployeeId()));

        assertThatThrownBy(() -> badgeService.save(createBadgeRequest))
                .isInstanceOf(IdNotFoundException.class)
                .hasMessageContaining("В базе данных нет employee с id=%s".formatted(createBadgeRequest.recipientEmployeeId()));
    }

    @Test
    void testSaveBadgeWithNotValidIssueEmployeeExpectedThrow() {

        CreateBadgeRequest createBadgeRequest = generateRequest();

        when(employeeService.getById(any())).thenReturn(new GetEmployeeResponse(
                createBadgeRequest.issuerEmployeeId(),
                Position.MANAGER,
                "Sales",
                Instant.now(),
                Instant.now()
        ));

        assertThatThrownBy(() -> badgeService.save(createBadgeRequest))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("Сотрудник с данным id = %s не может выдавать бейджики"
                        .formatted(createBadgeRequest.issuerEmployeeId()));
    }

    @Test
    void testDisableSuccess() {
        Badge badge = generateBadge();
        badgeService.disable(badge.id());

        verify(badgeDAO).disable(badge.id());
    }

    private CreateBadgeRequest generateRequest() {
        return new CreateBadgeRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "serial_number"
        );
    }


    private GetEmployeeResponse generateEmployeeResponse() {
        return new GetEmployeeResponse(
                UUID.randomUUID(),
                Position.SECURITY_OFFICER,
                "Security",
                Instant.now(),
                Instant.now()
        );
    }
}