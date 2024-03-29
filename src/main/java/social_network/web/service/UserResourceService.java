package social_network.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_network.web.domain.Post;
import social_network.web.domain.User;
import social_network.web.repository.PostRepository;
import social_network.web.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserResourceService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserResourceService(@Autowired UserRepository userRepository,
                               @Autowired PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public void RemoveUserInfoByUserId(Long userId){
        User u = findUserByIdOrThrow(userId);
        postRepository.findAll().forEach(
                post -> {
                    if (post.getLikes().contains(u)){
                        post.getLikes().remove(u);
                        postRepository.save(post);
                    }
                }
        );
    }


    private User findUserByIdOrThrow(Long id){
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return u;
    }

    private Post findPostByIdOrThrow(Long id){
        Post p = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return p;
    }

    public void likePost(Long userId, Long postId){
        User u = findUserByIdOrThrow(userId);
        Post p = findPostByIdOrThrow(postId);
        log.info("check if user {} already liked post {}", userId, postId);
        if (doILikePost(u, p) == -1){
            log.info("Can Like");
            p.getLikes().add(u);
            postRepository.save(p);
        }
    }

    public void unlikePost(Long userId, Long postId){
        User u = findUserByIdOrThrow(userId);
        Post p = findPostByIdOrThrow(postId);
        int idx = doILikePost(u, p);
        log.info("check if user {} already liked post {}", userId, postId);
        if (idx != -1) {
            log.info("Can Unlike");
            p.getLikes().remove(u);
            postRepository.save(p);
        }
    }

    public int doILikePost(User u, Post p){
        return p.getLikes().indexOf(u);
    }
}
