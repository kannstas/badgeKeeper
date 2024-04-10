package service;

import api.request.badge.CreateBadgeRequest;
import api.response.badge.GetBadgeResponse;
import api.response.employee.GetEmployeeResponse;
import dao.BadgeDAO;
import model.Badge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.BusinessLogicException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BadgeServiceImplUnitTest {
    @Mock
    private BadgeDAO badgeDAO;

    @Mock
    private EmployeeServiceImpl employeeService;

    @InjectMocks
    private BadgeServiceImpl badgeService;

    @BeforeEach()
    public void initMockito() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBadgeByIdSuccess() {
        // given
        Badge badge = createFakeBadge();
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
        Badge badge = createFakeBadge();
        when(badgeDAO.get(badge.id()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> badgeService.getById(badge.id()))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("В базе данных нет бейджика с id = %s".formatted(badge.id()))
                .hasMessageContaining(badge.id().toString());

        verify(badgeDAO).get(badge.id());
    }

    @Test
    void testGetAllBadgesSuccess() {
        Badge badge = createFakeBadge();
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
        GetEmployeeResponse recipientEmployeeResponse = createFakeEmployeeResponse();
        GetEmployeeResponse issueEmployeeResponse = createFakeEmployeeResponse();

        CreateBadgeRequest createBadgeRequest = new CreateBadgeRequest(
                recipientEmployeeResponse.id(),
                issueEmployeeResponse.id(),
                "serial_number"
        );

        when(employeeService.get(any()))
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

        CreateBadgeRequest createBadgeRequest = createFakeRequest();

        when(employeeService.get(createBadgeRequest.recipientEmployeeId()))
                .thenReturn(null);

        assertThatThrownBy(() -> badgeService.save(createBadgeRequest))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("В базе данных нет работника с таким id");
    }

    @Test
    void testSaveBadgeWithNotValidIssueEmployeeExpectedThrow() {

        CreateBadgeRequest createBadgeRequest = createFakeRequest();

        when(employeeService.get(any())).thenReturn(new GetEmployeeResponse(
                createBadgeRequest.issuerEmployeeId(),
                "Manager",
                "Sales",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        ));

        assertThatThrownBy(() -> badgeService.save(createBadgeRequest))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("Сотрудник с данным id = %s не может выдавать бейджики"
                        .formatted(createBadgeRequest.issuerEmployeeId()));
    }

    @Test
    void testDisableSuccess() {
        Badge badge = createFakeBadge();
        badgeService.disable(badge.id());

        verify(badgeDAO).disable(badge.id());
    }

    @Test
    void testDeleteSuccess() {
        Badge badge = createFakeBadge();
        badgeService.delete(badge.id());

        verify(badgeDAO).delete(badge.id());

    }

    private CreateBadgeRequest createFakeRequest() {
        return new CreateBadgeRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "serial_number"
        );
    }

    private Badge createFakeBadge() {
        return new Badge(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "badgeSerialNumber",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                true
        );
    }

    private GetEmployeeResponse createFakeEmployeeResponse() {
        return new GetEmployeeResponse(
                UUID.randomUUID(),
                "Security officer",
                "Security",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        );
    }
}