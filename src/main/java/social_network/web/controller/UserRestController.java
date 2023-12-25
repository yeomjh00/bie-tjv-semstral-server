package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_network.web.controller.asset.UserDto;
import social_network.web.domain.User;
import social_network.web.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDto> readAllUsers(){
        log.info("read all users");
        return userService.findAll().stream()
                .map(UserDto::User2Dto)
                .toList();
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(UserDto userDto){
        User user = User.Dto2User(userDto);
        if(userService.CheckValidityAndDuplicateAndStatus(user)){
            log.info("create user: {}", userDto);
            userService.save(user);
            return ResponseEntity.ok(UserDto.User2Dto(user));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<UserDto> readUserById(@PathVariable Long user_id){
        Optional<User> user = userService.findById(user_id);
        log.info("read user by id: {}", user_id);
        if(user.isEmpty()){
            log.info("user not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserDto.User2Dto(user.get()));
    }

    @PutMapping("/users/{user_id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long user_id, UserDto userDto){
        Optional<User> user = userService.findById(user_id);
        User tempUser = User.Dto2User(userDto);
        if (user.isEmpty()){
            log.info("user not found");
            return ResponseEntity.notFound().build();
        }
        else if (userService.CheckValidityAndDuplicateAndStatus(tempUser)){
            User u = user.get();
            log.info("update user: {}", userDto);
            u.setInfoFromDto(userDto);
            userService.save(u);
            return ResponseEntity.ok(UserDto.User2Dto(u));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/users/{user_id}")
    public void deleteUser(@PathVariable Long user_id){
        log.info("delete user by id: {}", user_id);
        userService.deleteById(user_id);
    }
}
