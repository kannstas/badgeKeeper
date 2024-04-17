package dao;

import model.Badge;
import model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static util.generator.badge.BadgeGenerator.generateBadge;
import static util.generator.employee.EmployeeGenerator.generateEmployee;

public class BadgeDaoTest extends AbstractPostgresContainerTest {
    private BadgeDAO badgeDAO;
    private EmployeeDAO employeeDAO;
    private Employee issueEmployee;
    private Employee resipientEmployee;
    private Badge badge;

    @BeforeEach()
    public void setup() {
        badgeDAO = new BadgeDAO();
        employeeDAO = new EmployeeDAO();
        issueEmployee = generateEmployee();
        resipientEmployee = generateEmployee();
        badge = generateBadge(resipientEmployee.id(), issueEmployee.id());
    }

    @BeforeEach
    public void cleanup() {
        truncateCascade("badges");
        truncateCascade("employees");
    }

    @Test
    void testGetByIdBadgeSuccess() {

        employeeDAO.save(resipientEmployee);
        employeeDAO.save(issueEmployee);

        badgeDAO.save(badge);

        Badge foundBadge = badgeDAO.get(badge.id()).orElseThrow();

        assertThat(foundBadge).isEqualTo(badge);
    }

    @Test
    void testGetAllBadgesSuccess() {
        employeeDAO.save(resipientEmployee);
        employeeDAO.save(issueEmployee);

        badgeDAO.save(badge);

        List<Badge> foundBadges = badgeDAO.getAll();

        assertEquals(1, foundBadges.size());
    }

    @Test
    void testSaveBadgeSuccess() {
        //given
        employeeDAO.save(resipientEmployee);
        employeeDAO.save(issueEmployee);

        //when
        badgeDAO.save(badge);

        Badge resultBadge = badgeDAO.get(badge.id()).orElseThrow();
        //then
        assertThat(resultBadge).isEqualTo(badge);
    }

    @Test
    void testBadgeNotFoundSuccess() {
        //given
        employeeDAO.save(generateEmployee());

        //when
        Optional<Badge> actual = badgeDAO.get(UUID.randomUUID());

        //then
        assertThat(actual).isEmpty();
    }

    @Test
    void testDisableSuccess() {
        employeeDAO.save(resipientEmployee);
        employeeDAO.save(issueEmployee);

        badgeDAO.save(badge);

        badgeDAO.disable(badge.id());

        Badge foundBadge = badgeDAO.get(badge.id()).orElseThrow();
        boolean badgeIsDisable = foundBadge.active();

        assertFalse(badgeIsDisable);

    }

}

