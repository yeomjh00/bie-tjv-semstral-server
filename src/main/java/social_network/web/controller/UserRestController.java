package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_network.web.controller.asset.UserDto;
import social_network.web.domain.User;
import social_network.web.service.UserResourceService;
import social_network.web.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class UserRestController {

    private final UserService userService;
    private final UserResourceService userResourceService;

    @Autowired
    public UserRestController(UserService userService,
                              UserResourceService userResoureService){
        this.userService = userService;
        this.userResourceService =  userResoureService;
    }

    @GetMapping("/users")
    public List<UserDto> readAllUsers(){
        log.info("read all users");
        return userService.findAll().stream()
                .map(UserDto::User2ShortDto)
                .toList();
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        User user = User.Dto2User(userDto);
        if(userService.CheckValidityAndDuplicateAndStatus(user)){
            log.info("create user: {}", userDto);
            userService.save(user);
            return ResponseEntity.ok(UserDto.User2Dto(user));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UserDto.userNotFound());
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
    public ResponseEntity<UserDto> updateUser(@PathVariable Long user_id, @RequestBody UserDto userDto){
        Optional<User> user = userService.findById(user_id);
        Optional<User> targetUsername = userService.findByUsername(userDto.getUsername());
        if (user.isEmpty()){ // user not found
            log.info("user not found");
            return ResponseEntity.notFound().build();
        } else if(targetUsername.isPresent()){ // username already exists
            log.info("username already exists");
            return ResponseEntity.badRequest().build();
        } else {
            log.info("update user: {}", userDto);
            User u = user.get();
            u.setInfoFromDto(userDto);
            userService.save(u);
            return ResponseEntity.ok(UserDto.User2Dto(u));
        }
    }

    @DeleteMapping("/users/{user_id}")
    public void deleteUser(@PathVariable Long user_id){
        Optional<User> user = userService.findById(user_id);
        if (user.isEmpty()){
            log.info("user not found");
            return;
        }
        log.info("delete user by id: {}", user_id);
        userResourceService.deleteUserInfoByUserId(user_id);
    }
}
