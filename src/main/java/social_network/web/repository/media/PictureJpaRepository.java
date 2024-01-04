package social_network.web.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import social_network.web.domain.Picture;

import java.util.List;

@Repository
public interface PictureJpaRepository extends JpaRepository<Picture, Long>, PictureRepository {

    @Override
    @Query("SELECT p FROM Picture p WHERE p.containedPost.id = ?1")
    List<Picture> findAllByPostId(Long postId);
}
