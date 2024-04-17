package util.dao;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class DAOUtils {
    public static <MODEL> Optional<MODEL> extractSingleResultOrNull(final List<MODEL> results) {
        if (results.size() > 1) {
            throw new RuntimeException("Result size is more than expected: " + results.size());
        }

        return results.stream().findFirst();
    }

    public static Timestamp toTimestamp(Instant instant) {
        return new Timestamp(instant.toEpochMilli());
    }
}