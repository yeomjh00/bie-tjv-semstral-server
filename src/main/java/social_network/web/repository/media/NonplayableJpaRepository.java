package social_network.web.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import social_network.web.domain.NonplayableMedia;

public interface NonplayableJpaRepository extends JpaRepository<NonplayableMedia, Long>, NonplayableRepository {
}