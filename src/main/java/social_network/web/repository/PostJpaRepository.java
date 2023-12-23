package social_network.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import social_network.web.domain.Post;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<Post, Long>, PostRepository {

    @Override
    @Modifying
    @Query("UPDATE Post p SET p.title = ?2, p.author = ?3, p.content = ?4 WHERE p.id = ?1")
    void updatePostById(Long id, Post post);

    @Override
    @Query("SELECT p FROM Post p WHERE p.author.id = ?1")
    List<Post> findAllByAuthorId(Long id);

    @Override
    @Query("SELECT p FROM Post p WHERE p.author.username = ?1")
    public List<Post> findAllByAuthorUsername(String username);

    @Override
    public List<Post> findLikedPostsByAuthorId(Long id);

    @Override
    @Query("SELECT p FROM Post p JOIN p.likes l WHERE l.id = ?1")
    public List<Post> findLikesByAuthorId(Long id);

    @Override
    void deleteByAuthorId(Long id);
}
