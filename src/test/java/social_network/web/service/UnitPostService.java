package social_network.web.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import social_network.web.controller.asset.PostDto;
import social_network.web.domain.Music;
import social_network.web.domain.Post;
import social_network.web.domain.User;
import social_network.web.repository.PostJpaRepository;
import social_network.web.repository.media.PictureRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UnitPostService {

    @InjectMocks
    PostService postService;

    @Mock
    PostJpaRepository postJpaRepository;

    @Mock
    PictureRepository pictureRepository;

    static List<User> userList = List.of(new User[]{
            User.builder().id(1L).username("user1").realName("user1").userStatus("membership").build(),
            User.builder().id(2L).username("user2").realName("user2").userStatus("membership").build(),
            User.builder().id(3L).username("user3").realName("user3").userStatus("membership").build(),
    });

    static List<Post> postList = List.of(new Post[]{
            Post.builder().id(1L).author(userList.get(0)).title("post1").content("post1").likes(List.of(userList.get(0))).pictures(Collections.emptyList()).build(),
            Post.builder().id(2L).author(userList.get(1)).title("post1").content("post2").likes(List.of(userList.get(0))).pictures(Collections.emptyList()).build(),
            Post.builder().id(3L).author(userList.get(2)).title("post1").content("post3").likes(List.of(userList.get(0))).pictures(Collections.emptyList()).build(),
            Post.builder().id(4L).author(userList.get(2)).title("post1").content("post3").likes(List.of(userList.get(1))).pictures(Collections.emptyList()).build(),
    });

    @BeforeEach
    void init(){
        when(postJpaRepository.findAll()).thenReturn(postList);
        when(postJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        for(var post : postList){
            when(postJpaRepository.findById(post.getId())).thenReturn(java.util.Optional.of(post));
            when(postJpaRepository.findByAuthor(post.getAuthor())).thenReturn(List.of(post));
        }
    }

    @AfterEach
    void tearDown(){
        reset(postJpaRepository);
    }

    @Test
    void saveValid() {
        //given
        Post p = postList.get(0);

        //when
        Post response = postService.save(p);
        p.setPictures(Collections.emptyList());

        //then
        assertThat(response).usingRecursiveComparison().isEqualTo(p);

    }

    @Test
    void saveInvalid(){
        //given
        Post nullContent = Post.builder().id(1L).author(userList.get(0)).content(null).title("h").pictures(Collections.EMPTY_LIST).build();
        Post nullTitle = Post.builder().id(1L).author(userList.get(0)).title(null).content("h").pictures(Collections.EMPTY_LIST).build();
        Post excessiveTitle = Post.builder().id(1L).author(userList.get(0)).title("h".repeat(256)).content("h").pictures(Collections.EMPTY_LIST).build();

        //when
        Post responseNullContent = postService.save(nullContent);
        Post responseNullTitle = postService.save(nullTitle);
        Post responseExcessiveTitle = postService.save(excessiveTitle);

        responseNullContent.setPictures(Collections.emptyList());
        responseNullTitle.setPictures(Collections.emptyList());
        responseExcessiveTitle.setPictures(Collections.emptyList());

        //then
        assertThat(responseNullContent).usingRecursiveComparison().isEqualTo(Post.Dto2Post(PostDto.invalidTitleOrContent(), nullContent.getAuthor()));
        assertThat(responseNullTitle).usingRecursiveComparison().isEqualTo(Post.Dto2Post(PostDto.invalidTitleOrContent(), nullTitle.getAuthor()));
        assertThat(responseExcessiveTitle).usingRecursiveComparison().isEqualTo(Post.Dto2Post(PostDto.invalidTitleOrContent(), excessiveTitle.getAuthor()));
    }

    @Test
    void findAllByAuthorId() {
        //given
        User u = userList.get(2);

        //when
        List<PostDto> response = postService.findAllByAuthorId(u.getId());

        //then
        assertThat(response)
                .extracting(PostDto::getAuthorUsername)
                .containsOnly(u.getUsername());
    }

    @Test
    void findAllByInvalidAuthorId() {
        //given
        Long invalidId = -1L;

        //when
        List<PostDto> response = postService.findAllByAuthorId(invalidId);

        //then
        assertThat(response).isEmpty();
    }

    @Test
    void editPost_emptyPost() {
        //given
        PostDto p = PostDto.Post2Dto(postList.get(0));
        p.setContent("");
        Music m = null;

        //when
        ResponseEntity<PostDto> response = postService.editPostContentAndTitle(p.getId(), p, m);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void editPost_invalidForm() {

        //given
        PostDto p = PostDto.Post2Dto(postList.get(0));
        p.setTitle("");
        Music m = null;

        //when
        ResponseEntity<PostDto> response = postService.editPostContentAndTitle(p.getId(), p, m);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void editPost() {
        //given
        PostDto p = PostDto.Post2Dto(postList.get(0));
        p.setContent("Hello");
        Music m = null;

        //when
        ResponseEntity<PostDto> response = postService.editPostContentAndTitle(p.getId(), p, m);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getContent()).isEqualTo(p.getContent());
    }

    @Test
    void findLikedPostsByUserId() {
        //given
        User u1 = userList.get(0);

        //when
        List<PostDto> response = postService.findLikedPostsByUserId(u1.getId());
        List<Long> responseIds = response.stream().map(PostDto::getId).toList();

        List<Long> actual = postList.stream().filter(post -> post.getLikes().contains(u1)).map(Post::getId).toList();

        //then
        assertThat(responseIds)
                .containsExactlyInAnyOrderElementsOf(actual);
    }

    @Test
    void findLikedPostsByNonExistedId() {
        //given
        Long invalidId = -1L;

        //when
        List<PostDto> response = postService.findLikedPostsByUserId(invalidId);

        //then
        assertThat(response).isEmpty();
    }
}