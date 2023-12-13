package social_network.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_network.web.domain.User;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

    @Override
    public Optional<User> findByUsername(String username);

    @Override
    public Optional<User> findByRealName(String realName);
}
