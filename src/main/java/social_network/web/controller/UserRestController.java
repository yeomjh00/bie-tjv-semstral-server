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
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;
    private final UserResourceService userResourceService;

    @Autowired
    public UserRestController(UserService userService,
                              UserResourceService userResoureService){
        this.userService = userService;
        this.userResourceService =  userResoureService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> readAllUsers(){
        log.info("read all users");
        List<UserDto> users = userService.findAll().stream()
                .map(UserDto::User2Dto)
                .toList();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        log.info("create user: {}", userDto);
        return userService.createUser(userDto);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> readUserById(@PathVariable Long user_id){
        return userService.readById(user_id);
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long user_id, @RequestBody UserDto userDto){
        return userService.updateUserInfo(user_id, userDto);
    }

    @DeleteMapping("/{user_id}")
    public HttpStatus deleteUser(@PathVariable Long user_id){
        userResourceService.RemoveUserInfoByUserId(user_id);
        return userService.deleteUser(user_id);
    }
}
