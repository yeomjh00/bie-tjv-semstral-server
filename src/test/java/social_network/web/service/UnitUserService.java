package social_network.web.service;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.web.domain.User;
import social_network.web.repository.UserJpaRepository;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


/* Consider only the saving method of UserService.
* Because the correctness of other methods depends on JPA repository
* */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserJpaRepository userJpaRepository;

    ArrayList<User> generateUserWithIdFrom(Long id, Long number, boolean membership){
        var userList = new ArrayList<User>();
        var membershipStatus = membership ? "membership" : "trial";
        for (int i = 0; i< number; i++){
            Long currentId = id + i;
            var user = new User(currentId, "user"+currentId, "user"+currentId, membershipStatus, "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            userList.add(user);
        }
        return userList;
    }


    @Test
    void save_duplicate_user() {
        var u1 = new User(1L, "user1", "user1", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        var u1_duplicate_username = new User(2L, "user1", "user2", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Mockito.when(userJpaRepository.findByUsername(u1.getUsername())).thenReturn(Optional.of(u1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(u1_duplicate_username));
    }

    @Test
    void save_null_user() {
        User u1 = null;
        Assertions.assertThrows(NullPointerException.class, () -> userService.save(u1));
    }

    @Test
    void save_illegal_username_user() {
        // case 1. username is empty
        var u1 = new User(1L, "", "user1", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Mockito.when(userJpaRepository.findByUsername(u1.getUsername())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(u1));

        // case 2. username is too long
        var u2 = new User(2L, "a".repeat(256), "user1", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Mockito.when(userJpaRepository.findByUsername(u2.getUsername())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(u2));
    }

    @Test
    void save_illegal_realName_user(){
        // case 1. realName is empty
        var u1 = new User(1L, "user1", "", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Mockito.when(userJpaRepository.findByUsername(u1.getUsername())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(u1));

        // case 2. realName is too long
        var u2 = new User(2L, "user1", "a".repeat(256), "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Mockito.when(userJpaRepository.findByUsername(u2.getUsername())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.save(u2));
    }
}