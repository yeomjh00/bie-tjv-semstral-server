package social_network.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import social_network.web.domain.Post;
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

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
        return;
    }

    public Post editPostContentAndTitle(Long id, String content, String title) {
        var post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post Id: {}" + id));
        post.setContent(content);
        post.setTitle(title);
        postRepository.updatePostById(post.getId(), post);
        return post;
    }
}
