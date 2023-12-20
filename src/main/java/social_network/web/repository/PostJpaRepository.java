package social_network.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import social_network.web.domain.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long>, PostRepository {

    @Override
    @Modifying
    @Query("UPDATE Post p SET p.title = ?2, p.author = ?3, p.content = ?4 WHERE p.id = ?1")
    void updatePostById(Long id, Post post);
}
