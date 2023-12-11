package social_network.web.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import social_network.web.domain.Post;
import social_network.web.domain.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository{
    List<Post> findByAuthor(User author);
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:pattern%")
    List<Post> findByPattern(@Param("pattern") String pattern);
}
