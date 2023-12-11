package social_network.web.repository;

import social_network.web.domain.User;

import java.util.Optional;

public interface UserRepository{
    Optional<User> findByUsername(String username);

    Optional<User> findByRealName(String realName);
}
