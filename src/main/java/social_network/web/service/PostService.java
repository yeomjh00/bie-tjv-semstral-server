package social_network.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_network.web.controller.asset.PostRegisterForm;
import social_network.web.domain.Post;
import social_network.web.domain.User;
import social_network.web.repository.PostRepository;
import social_network.web.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService implements CrudService<Post, Long> {


    private final PostRepository postRepository;

    public PostService(@Autowired PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post save(Post post) {
        postRepository.save(post);
        return post;
    }

    public boolean verifyTitleAndContent(String title, String content){
        if (title == null || title.isEmpty() || title.length() > 255){
            return false;
        } else if (content == null || content.isEmpty()){
            return false;
        }
        return true;
    }

    public Post verifiedSave(Post post){
        if (post.getContent() == null || post.getContent().isEmpty()){
            throw new IllegalArgumentException("Post content cannot be empty");
        } else if(post.getTitle() == null || post.getTitle().isEmpty() || post.getTitle().length() > 255){
            throw new IllegalArgumentException("Post title cannot be empty and exceed 255 characters");
        }
        return save(post);
    }

    public Post updatePostFromDto(Long postId, PostRegisterForm form) {
        if (verifyTitleAndContent(form.getTitle(), form.getContent())){
            var post = findByIdOrThrow(postId);
            post.setTitle(form.getTitle());
            post.setContent(form.getContent());
            postRepository.save(post);
            return post;
        } else {
            throw new IllegalArgumentException("Post title cannot be empty and exceed 255 characters");
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Post findByIdOrThrow(Long id){
        Post p = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return p;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public List<Post> findAllByAuthorId(Long id){
        return postRepository.findAllByAuthorId(id);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
        return;
    }

    void deleteByAuthorId(Long id){
        postRepository.deleteByAuthorId(id);
    }

    public Post editPostContentAndTitle(Long id, String content, String title) {
        var post = findByIdOrThrow(id);
        post.setContent(content);
        post.setTitle(title);
        postRepository.updatePostById(post.getId(), post);
        return post;
    }

    public List<Post> findMyPostsByUserId(Long id){ return postRepository.findAllByAuthorId(id); }

    public List<Post> findLikedPostsByUserId(Long id){ return postRepository.findLikesByAuthorId(id); }

}
