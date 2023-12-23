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

@Slf4j
@Service
public class UserResourceService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //@TODO: mediaRepository

    public UserResourceService(@Autowired UserRepository userRepository,
                               @Autowired PostRepository postRepository){
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public void deleteUserInfoByUserId(Long userId){
        User u = findUserByIdOrThrow(userId);
        postRepository.deleteByAuthorId(userId);
        userRepository.deleteById(userId);
        return;
    }

    @Transactional
    public void updateUserInfoByUserId(Long userId, User user){
        User u = findUserByIdOrThrow(userId);
        userRepository.save(user);
        ArrayList<Post> posts = (ArrayList<Post>) postRepository.findAllByAuthorId(userId);
        for (Post p : posts){
            p.setAuthor(user);
            postRepository.save(p);
        }
        return;
    }


    public User findUserByIdOrThrow(Long id){
        User u = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return u;
    }

    public Post findPostByIdOrThrow(Long id){
        Post p = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return p;
    }

    public void likePost(Long userId, Long postId){
        User u = findUserByIdOrThrow(userId);
        Post p = findPostByIdOrThrow(postId);
        if (doILikePost(userId, postId) == -1){
            p.getLikes().add(u);
            postRepository.save(p);
        }
    }

    public void unlikePost(Long userId, Long postId){
        User u = findUserByIdOrThrow(userId);
        Post p = findPostByIdOrThrow(postId);
        int idx = doILikePost(userId, postId);
        if (idx != -1) {
            p.getLikes().remove(u);
            postRepository.save(p);
        }
    }

    public int doILikePost(Long userId, Long postId){
        User u = findUserByIdOrThrow(userId);
        Post p = findPostByIdOrThrow(postId);
        return p.getLikes().indexOf(u);
    }
}
