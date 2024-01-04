package social_network.web.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_network.web.domain.MusicList;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicListJpaRepository extends JpaRepository<MusicList, Long>, MusicListRepository {

    @Override
    List<MusicList> findAllByOwnerId(Long userId);

    @Override
    @Query("SELECT COUNT(ml) FROM MusicList ml WHERE ml.owner.id = :ownerId")
    Long countListByUserId(@Param("ownerId") Long ownerId);
}
