package social_network.web.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_network.web.domain.MusicList;

@Repository
public interface MusicListJpaRepository extends JpaRepository<MusicList, Long>, MusicListRepository {
}
