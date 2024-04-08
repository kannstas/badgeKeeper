package service;

import java.util.List;
import java.util.UUID;

public interface Service<MODEL> {
    MODEL get(UUID id);

    List<MODEL> getAll();

    void save(MODEL model);

    void update(MODEL model);

    void delete(UUID id);

}