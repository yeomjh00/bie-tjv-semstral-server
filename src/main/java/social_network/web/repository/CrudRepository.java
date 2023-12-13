package social_network.web.repository;

import java.util.Optional;

public interface CrudRepository<T, ID>{
    T save(T entity);
    Optional<T> findById(ID id);
    Iterable<T> findAll();
    void deleteById(ID id);
}
