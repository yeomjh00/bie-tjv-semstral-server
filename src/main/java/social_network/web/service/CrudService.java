package social_network.web.service;

import social_network.web.repository.CrudRepository;
import social_network.web.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {

    public T save(T entity);

    public Optional<T> findById(ID id);

    public List<T> findAll();

    public void deleteById(ID id);
}
