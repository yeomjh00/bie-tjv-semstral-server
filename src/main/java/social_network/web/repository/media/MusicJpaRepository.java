package social_network.web.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_network.web.domain.Music;

@Repository
public interface MusicJpaRepository extends JpaRepository<Music, Long>, MusicRepository {

}
