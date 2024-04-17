package service;

import api.common.Position;
import api.request.badge.CreateBadgeRequest;
import api.response.badge.GetBadgeResponse;
import api.response.employee.GetEmployeeResponse;
import dao.BadgeDAO;
import jakarta.inject.Inject;
import util.convert.ConvertBadge;
import util.exception.BusinessLogicException;
import util.exception.IdNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class BadgeServiceImpl {
    @Inject
    private BadgeDAO badgeDAO;
    @Inject
    private EmployeeServiceImpl employeeService;

    Logger logger = Logger.getLogger(BadgeServiceImpl.class.getName());


    public GetBadgeResponse getById(UUID id) {
        requireNonNull(id);
        logger.info("get by id: start: id = %s".formatted(id));

        return badgeDAO.get(id)
                .map(ConvertBadge::toBadge)
                .orElseThrow(() -> new IdNotFoundException("badge", id));
    }


    public List<GetBadgeResponse> getAll() {
        logger.info("get all: start");

        return badgeDAO.getAll().stream()
                .map(ConvertBadge::toBadge)
                .toList();
    }

    public void save(CreateBadgeRequest badgeRequest) {
        requireNonNull(badgeRequest);
        logger.info("save badge: start: request = %s".formatted(badgeRequest));

        employeeService.getById(badgeRequest.recipientEmployeeId());

        GetEmployeeResponse issueEmployeeResponse = employeeService.getById(badgeRequest.issuerEmployeeId());

        if (issueEmployeeResponse.position() == Position.SECURITY_OFFICER) {
            badgeDAO.save(ConvertBadge.toBadge(badgeRequest));
        } else {
            throw new BusinessLogicException("Сотрудник с данным id = %s не может выдавать бейджики"
                    .formatted(issueEmployeeResponse.id()));
        }

    }

    public void disable(UUID id) {
        requireNonNull(id);
        logger.info("disable badge: start: id = %s".formatted(id));

        badgeDAO.disable(id);
    }

}