package social_network.web.service;

import ch.qos.logback.core.testUtil.MockInitialContext;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.web.domain.Post;
import social_network.web.domain.User;
import social_network.web.repository.PostRepository;
import social_network.web.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UnitUserResourceService {

    @InjectMocks
    UserResourceService userResourceService;

    @Mock
    UserRepository userRepository;

    @Mock
    PostRepository postRepository;

    static List<User> userList = List.of(new User[]{
            User.builder().id(1L).username("user1").realName("user1").userStatus("membership").build(),
            User.builder().id(2L).username("user2").realName("user2").userStatus("membership").build(),
            User.builder().id(3L).username("user3").realName("user3").userStatus("membership").build(),
    });

    static List<Post> postList  = List.of(new Post[]{
            Post.builder().id(1L).title("title1").content("content1").likes(new ArrayList<>()).build(),
            Post.builder().id(2L).title("title2").content("content2").likes(new ArrayList<>()).build(),
            Post.builder().id(3L).title("title3").content("content3").likes(new ArrayList<>()).build(),
    });


    @BeforeEach
    void init(){
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        for(var user : userList){
            Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        }

        Mockito.when(postRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        for(var post : postList){
            Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        }

        Mockito.when(postRepository.findAll()).thenReturn(postList);
    }

    @AfterEach
    void tearDown(){
        Mockito.reset(userRepository);
        Mockito.reset(postRepository);
    }

    @Test
    void likePost(){
        // given
        User u = userList.get(0);
        Post p = postList.get(0);

        // when
        userResourceService.likePost(u.getId(), p.getId());

        // then
        assertThat(p.getLikes().contains(u)).isTrue();
    }

    @Test
    void likePostByNonExistUser(){
        // given
        User u = userList.get(0);
        Post p = postList.get(0);

        // then
        assertThatThrownBy(() -> userResourceService.likePost(u.getId() + 100, p.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User not found");
    }

    @Test
    void unlikePost(){
        // given
        User u = userList.get(0);
        Post p = postList.get(0);

        // when
        userResourceService.likePost(u.getId(), p.getId());
        Mockito.when(postRepository.save(p)).thenReturn(p);
        userResourceService.unlikePost(u.getId(), p.getId());

        // then
        assertThat(p.getLikes().contains(u)).isFalse();
    }

    @Test
    void unlikePostByOtherUser(){
        //given
        User u1 = userList.get(0);
        User u2 = userList.get(1);
        Post p = postList.get(0);

        p.getLikes().add(u1);
        UserResourceService urs = Mockito.mock(UserResourceService.class);
        Mockito.when(postRepository.findById(p.getId())).thenReturn(Optional.of(p));

        // when, then
        Mockito.verify(urs, Mockito.times(0)).unlikePost(u2.getId(), p.getId());
    }
}