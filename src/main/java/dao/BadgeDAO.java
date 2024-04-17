package dao;

import model.Badge;
import util.dao.DAOUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BadgeDAO extends AbstractDao<Badge, UUID> {

    private final String SELECT_BADGE =
            """
                    SELECT
                       id,
                       recipient_employee_id,
                       issuer_employee_id,
                       badge_serial_number,
                       issuance_date,
                       expiration_date,
                       active
                    FROM badges
                    """;

    public Optional<Badge> get(UUID id) {

        String sql = "%s WHERE id='%s'".formatted(SELECT_BADGE, id);

        return DAOUtils.extractSingleResultOrNull(
                fetch(sql)
        );
    }

    public List<Badge> getAll() {
        return fetch(SELECT_BADGE);
    }

    public void save(Badge badge) {

        executeUpdate(
                """
                        INSERT INTO badges(
                           id,
                           recipient_employee_id,
                           issuer_employee_id,
                           badge_serial_number,
                           issuance_date,
                           expiration_date,
                           active)
                        VALUES (?,?,?,?,?,?,?);
                        """,
                Map.of(
                        1, badge.id(),
                        2, badge.recipientEmployeeId(),
                        3, badge.issuerEmployeeId(),
                        4, badge.badgeSerialNumber(),
                        5, DAOUtils.toTimestamp(badge.issuanceDate()),
                        6, DAOUtils.toTimestamp(badge.expirationDate()),
                        7, badge.active()
                )
        );
    }

    public void disable(UUID id) {

        executeUpdate(
                """
                        UPDATE badges
                        SET
                           active=false
                        WHERE id='%s';
                        """.formatted(id)
        );
    }

    @Override
    protected Badge resultSetToModel(ResultSet resultSet) throws SQLException {

        return new Badge(
                (UUID) resultSet.getObject("id"),
                (UUID) resultSet.getObject("recipient_employee_id"),
                (UUID) resultSet.getObject("issuer_employee_id"),
                resultSet.getString("badge_serial_number"),
                resultSet.getTimestamp("issuance_date").toInstant(),
                resultSet.getTimestamp("expiration_date").toInstant(),
                resultSet.getBoolean("active")
        );
    }
}