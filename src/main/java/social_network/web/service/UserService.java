package social_network.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_network.web.domain.User;
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
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByRealName(String realName) {
        return userRepository.findByRealName(realName);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(Long id) { userRepository.deleteById(id); }



    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean CheckValidityAndDuplicateAndStatus(User user){
        if (user == null || user.getUsername() == null || user.getRealName() == null || user.getUserStatus() == null){
            log.info("Null User is trying to be created");
            return false;
        }
        boolean usernameExists = existsByUsername(user.getUsername());
        boolean usernameLengthInvalid = user.getUsername().length() > 255 ||
                user.getUsername().isEmpty();
        boolean realNameLengthInvalid = user.getRealName().length() > 255 ||
                user.getRealName().isEmpty();
        boolean userStatusValid = CheckUserStatusValid(user);
        log.info("Validity Check: usernameExists: {}, usernameLengthInvalid: {}, realNameLengthInvalid: {}, userStatusInvalid: {}",
                usernameExists, usernameLengthInvalid, realNameLengthInvalid, userStatusValid);
        return !usernameExists && !usernameLengthInvalid && !realNameLengthInvalid && userStatusValid;
    }

    public boolean CheckUserStatusValid(User user){
        return user.getUserStatus().equals("trial") ||
                user.getUserStatus().equals("membership");
    }

    public boolean CheckUsername(String username) {
        boolean usernameExists = existsByUsername(username);
        boolean usernameLengthInvalid = username.length() > 255 ||
                username.isEmpty();
        log.info("Validity Check: usernameExists: {}, usernameLengthInvalid: {}",
                usernameExists, usernameLengthInvalid);
        return !usernameExists && !usernameLengthInvalid;
    }
}
