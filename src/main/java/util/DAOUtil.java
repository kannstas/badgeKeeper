package util;

import java.util.List;
import java.util.Optional;

public class DAOUtil {
    public  <MODEL> Optional<MODEL> extractSingleResultOrNull(final List<MODEL> results) {
        if (results.size() > 1) {
            throw new RuntimeException("Result size is more than expected: " + results.size());
        }

        return results.stream().findFirst();
    }
}