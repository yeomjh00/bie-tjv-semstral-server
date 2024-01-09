package social_network.web.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import social_network.web.domain.User;

import java.util.ArrayList;
import java.util.ArrayList;

@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserJpaRepositoryTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    void findByUsername() {
        //given
        User u1 = generateUserWithId(1L);
        userJpaRepository.save(u1);

        //when
        var u1_found = userJpaRepository.findByUsername(u1.getUsername());

        //then
        Assertions.assertThat(u1).usingRecursiveComparison().isEqualTo(u1_found.get());
    }

    @Test
    void updateUserById() {
        // given
        User u1 = generateUserWithId(1L);
        userJpaRepository.save(u1);

        //when
        u1.setRealName("user2");
        u1.setUsername("user2");
        userJpaRepository.save(u1);

        //then
        var u1_updated = userJpaRepository.findById(u1.getId()).get();
        Assertions.assertThat(u1).usingRecursiveComparison().isEqualTo(u1_updated);
        Assertions.assertThat(userJpaRepository.findAll().size()).isEqualTo(1);
    }

    User generateUserWithId(Long id) {
        return User.builder()
                .id(id)
                .username("user" + id)
                .realName("user" + id)
                .userStatus("trial")
                .myPosts(new ArrayList<>())
                .likedPosts(new ArrayList<>())
                .myMusicLists(new ArrayList<>())
                .build();
    }
}