package social_network.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_network.web.controller.asset.UserDto;
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
        log.info("save user with username: {}, realName: {}, userStatus: {}, introduction: {}",
                user.getUsername(), user.getRealName(), user.getUserStatus(), user.getIntroduction());
        userRepository.save(user);
        return user;
    }

    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        User user = User.Dto2User(userDto);
        if(!CheckValidityAndStatus(user)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserDto.invalidName());

        }
        if(existsByUsername(user.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserDto.duplicatedUserName());
        }
        save(user);
        return ResponseEntity.ok(UserDto.User2Dto(user));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public ResponseEntity<UserDto> readById(Long user_id) {
        Optional<User> user = findById(user_id);
        log.info("read user by id: {}", user_id);
        if(user.isEmpty()){
            log.info("user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UserDto.userNotFound());
        }
        return ResponseEntity.ok(UserDto.User2Dto(user.get()));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
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

    public boolean CheckValidityAndStatus(User user){
        if (user == null || user.getUsername() == null || user.getRealName() == null || user.getUserStatus() == null){
            log.info("Null User is trying to be created");
            return false;
        }
        boolean usernameLengthInvalid = user.getUsername().length() > 255 ||
                user.getUsername().isEmpty();
        boolean realNameLengthInvalid = user.getRealName().length() > 255 ||
                user.getRealName().isEmpty();
        boolean userStatusValid = CheckUserStatusValid(user);
        log.info("Validity Check: usernameLengthInvalid: {}, realNameLengthInvalid: {}, userStatusInvalid: {}",
                usernameLengthInvalid, realNameLengthInvalid, userStatusValid);
        return !usernameLengthInvalid && !realNameLengthInvalid && userStatusValid;
    }

    public boolean CheckUserStatusValid(User user){
        return user.getUserStatus().equals("trial") ||
                user.getUserStatus().equals("membership");
    }

    public boolean CheckUsername(String username) {
        boolean usernameLengthInvalid = username.length() > 255 ||
                username.isEmpty();
        return !usernameLengthInvalid;
    }

    public ResponseEntity<UserDto> updateUserInfo(Long user_id, UserDto userDto){
        Optional<User> user = findById(user_id);
        if (user.isEmpty()){ // user not found
            log.info("user not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UserDto.userNotFound());
        }
        if(! CheckUsername(userDto.getUsername())){
            log.info("username length is invalid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserDto.invalidName());
        }
        Optional<User> targetUsername = findByUsername(userDto.getUsername());
        if(targetUsername.isPresent()
                && !targetUsername.get().getId().equals(user.get().getId())){ // username already exists
            log.info("username already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserDto.duplicatedUserName());
        }
        log.info("update user: {}", userDto);
        User u = user.get();
        u.setInfoFromDto(userDto);
        save(u);
        return ResponseEntity.ok(UserDto.User2Dto(u));
    }

    public HttpStatus deleteUser(Long user_id){
        Optional<User> user = findById(user_id);
        if (user.isEmpty()){
            log.info("user not found");
            return HttpStatus.NOT_FOUND;
        }
        log.info("delete user by id: {}", user_id);
        deleteById(user_id);
        return HttpStatus.OK;
    }
}
