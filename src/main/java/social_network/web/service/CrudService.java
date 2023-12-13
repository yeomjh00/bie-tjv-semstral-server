package social_network.web.service;

import social_network.web.repository.CrudRepository;

import java.util.Optional;

public class CrudService<T, ID>{
    private final CrudRepository<T, ID> repository;

    public CrudService(CrudRepository<T, ID> repository) {
        this.repository = repository;
    }

    public T save(T entity) {
        return null;
    }

    public Optional<T> findById(ID id) {
        return null;
    }

    public Iterable<T> findAll() {
        return null;
    }

    public void deleteById(ID id) {
    }
}
