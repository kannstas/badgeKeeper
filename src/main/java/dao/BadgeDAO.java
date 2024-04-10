package dao;

import jakarta.inject.Inject;
import model.Badge;
import util.DAOUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static java.util.Objects.requireNonNull;

public class BadgeDAO extends AbstractDao<Badge, UUID> {

    @Inject
    private DAOUtil daoUtil;
    private final String SELECT_BADGE = """
            SELECT
            id, recipient_employee_id, issuer_employee_id, badge_serial_number, issuance_date, expiration_date, active
            FROM badges
            """;

    private final String INSERT_BADGE = """
            INSERT INTO badges
            (id, recipient_employee_id, issuer_employee_id, badge_serial_number, issuance_date, expiration_date, active)
            VALUES (?,?,?,?,?,?,?)
            """;

    private final String DISABLE_BADGE = """
            UPDATE badges
            SET active=false
            """;

    private final String DELETE_BADGE = """
            DELETE
            FROM badges
            """;
    public Optional<Badge> get(UUID id) {
        requireNonNull(id);

        String sql = "%s WHERE id='%s'".formatted(SELECT_BADGE, id);

        return daoUtil.extractSingleResultOrNull(
                fetch(sql)
        );
    }

    public List<Badge> getAll() {
        return fetch(SELECT_BADGE);
    }

    public void save(Badge badge) {
        requireNonNull(badge);

        executeUpdate(
                INSERT_BADGE,
                badge.id(),
                badge.recipientEmployeeId(),
                badge.issuerEmployeeId(),
                badge.badgeSerialNumber(),
                badge.issuanceDate(),
                badge.expirationDate(),
                badge.active()
        );
    }

    @Override
    protected Badge resultSetToModel(ResultSet resultSet) throws SQLException {
        requireNonNull(resultSet);

        return new Badge(
                (UUID) resultSet.getObject("id"),
                (UUID) resultSet.getObject("recipient_employee_id"),
                (UUID) resultSet.getObject("issuer_employee_id"),
                resultSet.getString("badge_serial_number"),
                resultSet.getTimestamp("issuance_date"),
                resultSet.getTimestamp("expiration_date"),
                resultSet.getBoolean("active")
        );
    }

    public void disable (UUID id) {
        requireNonNull(id);

        String sql = "%s WHERE id='%s'".formatted(DISABLE_BADGE, id);
        executeUpdate(sql);
    }


    public void delete(UUID id) {
        requireNonNull(id);

        String sql = "%s WHERE id='%s'".formatted(DELETE_BADGE, id);
        executeUpdate(sql);
    }
}