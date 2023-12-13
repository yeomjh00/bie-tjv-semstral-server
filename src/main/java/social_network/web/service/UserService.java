package social_network.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import social_network.web.domain.User;
import social_network.web.repository.UserRepository;

@Transactional
public class UserService {
    private final UserRepository userRepository;
    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByRealName(String realName) {
        return userRepository.findByRealName(realName).orElse(null);
    }

}
