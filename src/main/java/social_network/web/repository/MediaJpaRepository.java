package social_network.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social_network.web.domain.Media;

public interface MediaJpaRepository extends JpaRepository<Media, Long>, MediaRepository {
}
