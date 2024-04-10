package service;

import api.request.badge.CreateBadgeRequest;
import api.response.badge.GetBadgeResponse;
import api.response.employee.GetEmployeeResponse;
import dao.BadgeDAO;
import jakarta.inject.Inject;
import util.BusinessLogicException;
import util.convert.ConvertBadge;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class BadgeServiceImpl {
    @Inject
    private BadgeDAO badgeDAO;
    @Inject
    private EmployeeServiceImpl employeeService;


    public GetBadgeResponse getById(UUID id) {
        requireNonNull(id);

        return badgeDAO.get(id)
                .map(ConvertBadge::badgeToBadgeResponse)
                .orElseThrow(() -> new BusinessLogicException("В базе данных нет бейджика с id = %s ".formatted(id)));
    }


    public List<GetBadgeResponse> getAll() {
        return badgeDAO.getAll().stream()
                .map(ConvertBadge::badgeToBadgeResponse)
                .toList();
    }

    public void save(CreateBadgeRequest badgeRequest) {
        requireNonNull(badgeRequest);

        GetEmployeeResponse recipientEmployeeResponse = employeeService.get(badgeRequest.recipientEmployeeId());

        if (recipientEmployeeResponse == null) {
            throw new BusinessLogicException("В базе данных нет работника с таким id");
        }

        GetEmployeeResponse issueEmployeeResponse = employeeService.get(badgeRequest.issuerEmployeeId());

        if (issueEmployeeResponse.position().equals("Security officer")) {
            badgeDAO.save(ConvertBadge.badgeRequestToBadge(badgeRequest));
        } else throw new BusinessLogicException("Сотрудник с данным id = %s не может выдавать бейджики"
                .formatted(issueEmployeeResponse.id()));

    }

    public void disable(UUID id) {
        requireNonNull(id);

        badgeDAO.disable(id);
    }

    public void delete(UUID id) {
        requireNonNull(id);

        badgeDAO.delete(id);
    }


}