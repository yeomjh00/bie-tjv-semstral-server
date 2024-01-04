package social_network.web.repository.media;

import org.springframework.data.repository.query.Param;
import social_network.web.domain.MusicList;
import social_network.web.repository.CrudRepository;

import java.util.List;

public interface MusicListRepository extends CrudRepository<MusicList, Long> {

    List<MusicList> findAllByOwnerId(Long userId);

    Long countListByUserId(@Param("ownerId") Long ownerId);
}
