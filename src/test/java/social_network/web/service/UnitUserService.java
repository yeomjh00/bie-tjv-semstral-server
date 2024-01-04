package social_network.web.service;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import social_network.web.controller.asset.UserDto;
import social_network.web.domain.User;
import social_network.web.repository.UserJpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/* Consider only the saving method of UserService.
* Because the correctness of other methods depends on JPA repository
* */
@ExtendWith(MockitoExtension.class)
class UnitUserService {

    @InjectMocks
    UserService userService;

    @Mock
    UserJpaRepository userJpaRepository;

    static List<User> userList = List.of(new User[]{
            User.builder().id(1L).username("user1").realName("user1").userStatus("membership").build(),
            User.builder().id(2L).username("user2").realName("user2").userStatus("membership").build(),
            User.builder().id(3L).username("user3").realName("user3").userStatus("membership").build(),
            User.builder().id(4L).username("user4").realName("user4").userStatus("membership").build(),
            User.builder().id(5L).username("user5").realName("user5").userStatus("membership").build(),
            User.builder().id(6L).username("user6").realName("user6").userStatus("trial").build(),
            User.builder().id(7L).username("user7").realName("user7").userStatus("trial").build(),
            User.builder().id(8L).username("user8").realName("user8").userStatus("trial").build(),
            User.builder().id(9L).username("user9").realName("user9").userStatus("trial").build(),
            User.builder().id(10L).username("user10").realName("user10").userStatus("trial").build()
    });

    @BeforeEach
    void init(){
        Mockito.when(userJpaRepository.findAll()).thenReturn(userList);
        for(var user : userList){
            Mockito.when(userJpaRepository.findById(user.getId())).thenReturn(Optional.of(user));
            Mockito.when(userJpaRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        }
    }

    @AfterEach
    void tearDown(){
        Mockito.reset(userJpaRepository);
    }

    @Test
    void createUser(){
        var u11 = new User(11L, "user11", "user11", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        var u11Dto = UserDto.User2Dto(u11);
        Mockito.when(userJpaRepository.findByUsername(u11.getUsername())).thenReturn(Optional.empty());
        ResponseEntity<UserDto> result = userService.createUser(u11Dto);
        Assertions.assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(u11Dto);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void create_duplicate_user() {
        var u1 = new User(1L, "user1", "user1", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ResponseEntity<UserDto> result = userService.createUser(UserDto.User2Dto(u1));
        Assertions.assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(UserDto.duplicatedUserName());
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void save_null_user() {
        User u1 = null;
        Assertions.assertThatThrownBy(() -> userService.save(u1))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void readByNegativeId(){
        var u_1 = new User(-1L, "user-1", "user-1", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Mockito.when(userJpaRepository.findById(u_1.getId())).thenReturn(Optional.empty());
        ResponseEntity<UserDto> result = userService.readById(u_1.getId());
        Assertions.assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(UserDto.userNotFound());
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void saveIllegalRealName(){
        // case 1. realName is empty
        var u1 = new User(1L, "user1", "", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ResponseEntity<UserDto> response1 = userService.createUser(UserDto.User2Dto(u1));
        Assertions.assertThat(response1.getBody()).usingRecursiveComparison().isEqualTo(UserDto.invalidName());
        Assertions.assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        // case 2. realName is too long
        var u2 = new User(2L, "user1", "a".repeat(256), "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ResponseEntity<UserDto> response2 = userService.createUser(UserDto.User2Dto(u1));
        Mockito.when(userJpaRepository.findByUsername(u2.getUsername())).thenReturn(Optional.empty());
        Assertions.assertThat(response2.getBody()).usingRecursiveComparison().isEqualTo(UserDto.invalidName());
        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void saveIllegalUsername() {
        // case 1. username is empty
        var u1 = new User(1L, "", "user1", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ResponseEntity<UserDto> response1 = userService.createUser(UserDto.User2Dto(u1));
        Assertions.assertThat(response1.getBody()).usingRecursiveComparison().isEqualTo(UserDto.invalidName());
        Assertions.assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        // case 2. username is too long
        var u2 = new User(2L, "a".repeat(256), "user1", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        ResponseEntity<UserDto> response2 = userService.createUser(UserDto.User2Dto(u1));
        Mockito.when(userJpaRepository.findByUsername(u2.getUsername())).thenReturn(Optional.empty());
        Assertions.assertThat(response2.getBody()).usingRecursiveComparison().isEqualTo(UserDto.invalidName());
        Assertions.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    //    public ResponseEntity<UserDto> updateUserInfo(Long user_id, UserDto userDto)

    @Test
    void updateUserInfoSuccess(){
        var u1 = new User(1L, "user1", "user1", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        var u1_modified = new User(1L, "user14", "user1", "membership", "Hi", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        var u1Dto = UserDto.User2Dto(u1);
        var u1_modifiedDto = UserDto.User2Dto(u1_modified);

        Mockito.when(userJpaRepository.findByUsername(u1_modifiedDto.getUsername())).thenReturn(Optional.empty());

        ResponseEntity<UserDto> result = userService.updateUserInfo(u1.getId(), u1Dto);

        Assertions.assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(u1Dto);
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateUserInfoFailure(){
        //given
        var u1 = new User(1L, "user1", "user1", "trial", "", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        var notFound = new User(110L, "user14", "user1", "membership", "Hi", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        var alreadyExists = new User(3L, "user2", "user1", "membership", "Hi", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        var invalidName = new User(3L, "a".repeat(256), "user1", "membership", "Hi", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        var dto_notFound = UserDto.User2Dto(notFound);
        var dto_alreadyExists = UserDto.User2Dto(alreadyExists);
        var dto_invalidName = UserDto.User2Dto(invalidName);

        //when
        Mockito.when(userJpaRepository.findById(dto_notFound.getId())).thenReturn(Optional.empty());
        Mockito.when(userJpaRepository.findByUsername(dto_notFound.getUsername())).thenReturn(Optional.empty());
        Mockito.when(userJpaRepository.findByUsername(dto_invalidName.getUsername())).thenReturn(Optional.empty());


        ResponseEntity<UserDto> result1 = userService.updateUserInfo(dto_notFound.getId(), dto_notFound);
        Assertions.assertThat(result1.getBody()).usingRecursiveComparison().isEqualTo(UserDto.userNotFound());
        Assertions.assertThat(result1.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        ResponseEntity<UserDto> result2 = userService.updateUserInfo(dto_alreadyExists.getId(), dto_alreadyExists);
        Assertions.assertThat(result2.getBody()).usingRecursiveComparison().isEqualTo(UserDto.duplicatedUserName());
        Assertions.assertThat(result2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ResponseEntity<UserDto> result3 = userService.updateUserInfo(dto_invalidName.getId(), dto_invalidName);
        Assertions.assertThat(result3.getBody()).usingRecursiveComparison().isEqualTo(UserDto.invalidName());
        Assertions.assertThat(result3.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);


    }
}