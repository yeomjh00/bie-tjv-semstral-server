package social_network.web.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import social_network.web.domain.PlayableMedia;

public interface PlayableJpaRepository extends JpaRepository<PlayableMedia, Long>, PlayableRepository{

}
