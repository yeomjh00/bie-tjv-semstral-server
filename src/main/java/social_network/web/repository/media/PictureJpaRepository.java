package social_network.web.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_network.web.domain.Picture;

@Repository
public interface PictureJpaRepository extends JpaRepository<Picture, Long>, PictureRepository {
}
