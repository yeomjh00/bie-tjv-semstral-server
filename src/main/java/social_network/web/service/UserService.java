package social_network.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_network.web.domain.User;
import social_network.web.repository.UserJpaRepository;
import social_network.web.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserService implements CrudService<User, Long> {
    private final UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        duplicateAndNullCheck(user);
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        return;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByRealName(String realName) {
        return userRepository.findByRealName(realName);
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void duplicateAndNullCheck(User user){
        if (user == null){
            throw new NullPointerException("User is null");
        }
        else if (existsByUsername(user.getUsername())){
            log.error("Username already exists" + user.getUsername());
            log.error("repository result: ");
            log.error(userRepository.findByUsername(user.getUsername()).get().getUsername());
            throw new IllegalArgumentException("Username already exists");
        }
        else if (user.getUsername().length() > 255 ||
                user.getUsername().isEmpty()){
            throw new IllegalArgumentException("Username must be less than 255 characters and not empty");
        }
        else if (user.getRealName().length() > 255 ||
                user.getRealName().isEmpty()){
            throw new IllegalArgumentException("Real name must be less than 255 characters and not empty");
        }
    }
}
