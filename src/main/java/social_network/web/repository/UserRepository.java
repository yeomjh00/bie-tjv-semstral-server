package social_network.web.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import social_network.web.domain.Post;
import social_network.web.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>{ //
    Optional<User> findByUsername(String username);

    Optional<User> findByRealName(String realName);
}
