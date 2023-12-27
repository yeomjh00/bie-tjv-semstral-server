package social_network.web.repository;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import social_network.web.domain.Post;
import social_network.web.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class PostJpaRepositoryTest {

    @Autowired
    PostJpaRepository postJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @BeforeEach
    public void beforeEach(){
        User u = userGenerator(1L);
        userJpaRepository.save(u);

    }

    @AfterEach
    public void afterEach(){
        userJpaRepository.deleteAll();
        postJpaRepository.deleteAll();
    }

    @Test
    public void savePost(){
        //given
        User u = userJpaRepository.findByUsername("user1").get();
        Post p = postGenerator(1L, u);
        postJpaRepository.save(p);

        //when
        Post saved = postJpaRepository.findByAuthor(u).get(0);

        //then
        Assertions.assertThat(p).usingRecursiveComparison().isEqualTo(saved);
    }

    @Test
    public void findAllByAuthorId(){
        //given
        User u = userJpaRepository.findByUsername("user1").get();
        ArrayList<Post> posts = generatePostFromId(8L, 5L, u);

        //when
        List<Post> postsSaved = postJpaRepository.findAllByAuthorId(u.getId());

        //then
        Assertions.assertThat(posts.size()).as("Size Check").isEqualTo(postsSaved.size());
        Assertions.assertThat(posts).as("{} does not match with {}", posts, postsSaved)
                .usingRecursiveComparison().isEqualTo(postsSaved);
    }

    @Test
    public void findAllByAuthorUsername(){
        //given
        User u = userJpaRepository.findByUsername("user1").get();
        ArrayList<Post> posts1 = generatePostFromId(1L, 3L, u);

        User u2 = userGenerator(2L);
        userJpaRepository.save(u2);
        ArrayList<Post> posts2 = generatePostFromId(1L, 4L, u2);


        //when
        List<Post> postsOfUser1 = postJpaRepository.findAllByAuthorUsername("user1");
        List<Post> postsOfUser2 = postJpaRepository.findAllByAuthorUsername("user2");

        System.out.print("here1: "+ posts1.size());
        System.out.print("here2: "+ posts2.size());
        System.out.print("here1: "+ postsOfUser1.size());
        System.out.print("here2: "+ postsOfUser2.size());
        //then
        assertEquals(posts1.size(), postsOfUser1.size());
        assertEquals(posts2.size(), postsOfUser2.size());
    }

    @Test
    public void deleteByAuthorId(){
        //given
        User u = userJpaRepository.findByUsername("user1").get();
        ArrayList<Post> posts = generatePostFromId(1L, 3L, u);

        User u2 = userGenerator(2L);
        userJpaRepository.save(u2);
        Post anotherPost = postGenerator(1L, u2);
        postJpaRepository.save(anotherPost);

        //when
        postJpaRepository.deleteByAuthorId(u.getId());
        List<Post> postsOfUser1 = postJpaRepository.findAllByAuthorId(u.getId());
        List<Post> allPost = postJpaRepository.findAll();

        //then
        assertEquals(0, postsOfUser1.size());
        assertEquals(1, allPost.size());
    }

    //TODO: Like
    //@Test
    public void findLikedPostsById(){
        //given
        User u = userJpaRepository.findByUsername("user1").get();
        Post p = Post.builder()
                .title("title")
                .author(u)
                .content("content")
                .build();
        postJpaRepository.saveAndFlush(p);

        //when - set each other
        List<Post> expectedLikedPost = List.of(p);
        p.setLikes(new ArrayList<User>(List.of(u)));

        //when - saving
        postJpaRepository.save(p);
        ArrayList<Post> posts = generatePostFromId(1L, 3L, u);
        ArrayList<Post> actualLikedPosts = (ArrayList<Post>) postJpaRepository.findLikesByAuthorId(u.getId());

        //then
        Assertions.assertThat(actualLikedPosts.size()).as("Size Check").isEqualTo(1);
        Assertions.assertThat(actualLikedPosts).as("{} does not match with {}", expectedLikedPost, actualLikedPosts)
                .usingRecursiveComparison().isEqualTo(expectedLikedPost);
    }
//    @Test
//    public void findLikedPostsById(){
//        //given
//        User u = userJpaRepository.findByUsername("user1").get();
//        Post p = Post.builder()
//                .id(6L)
//                .title("title")
//                .author(u)
//                .content("content")
//                .build();
//        postJpaRepository.save(p);
//
//        List<Post> expectedLikedPost = List.of(p);
//        u.setLikedPosts(expectedLikedPost);
//        p.setLikes(List.of(u));
//        u.setLikedPosts(List.of(p));
//        userJpaRepository.save(u);
//        postJpaRepository.save(p);
//        ArrayList<Post> posts = generatePostFromId(1L, 3L, u);
//        User u_find = userJpaRepository.findById(1L).get();
//
//        //when
//        List<Post> actualLikedPosts = postJpaRepository.findLikesByAuthorId(1L);
//
//        //then
//        Assertions.assertThat(u).usingRecursiveComparison().isEqualTo(u_find);
//        Assertions.assertThat(actualLikedPosts.size()).as("Size Check").isEqualTo(1);
//        Assertions.assertThat(actualLikedPosts).as("{} does not match with {}", expectedLikedPost, actualLikedPosts)
//                .usingRecursiveComparison().isEqualTo(expectedLikedPost);
//
//        userJpaRepository.deleteAll();
//        postJpaRepository.deleteAll();
//    }

    public ArrayList<Post> generatePostFromId(Long id, Long number, User user){
        ArrayList<Post> posts = new ArrayList<>();
        for (long i = 0; i < number ; i++){
            Post p = Post.builder()
                    .title("title"+i)
                    .author(user)
                    .content("content")
                    .build();
            postJpaRepository.save(p);
            posts.add(p);
        }
        return posts;
    }

    public User userGenerator(Long id){
        User u = User.builder()
                .id(id)
                .username("user"+id)
                .realName("user"+id)
                .introduction("introduction")
                .userStatus("trial")
                .build();
        return u;
    }

    public Post postGenerator(Long id, User user){
        Post p = Post.builder()
                .id(id)
                .title("title"+id)
                .author(user)
                .content("content")
                .build();
        return p;
    }

}