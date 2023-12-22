package social_network.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import social_network.web.domain.Post;
import social_network.web.domain.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long>{
    List<Post> findByAuthor(User author);
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:pattern%")
    List<Post> findByPattern(@Param("pattern") String pattern);

    List<Post> findAllByAuthorId(Long id);

    public List<Post> findLikedPostsByAuthorId(Long id);
    public List<Post> findLikesByAuthorId(Long id);

    void updatePostById(Long id, Post post);

    void deleteByAuthorId(Long id);
}
