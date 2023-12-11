package social_network.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social_network.web.domain.Post;

public interface PostJpaRepository extends JpaRepository<Post, Long>, PostRepository {

}
