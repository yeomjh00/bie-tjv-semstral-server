package social_network.web.repository.media;

import social_network.web.domain.Music;
import social_network.web.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MusicRepository extends CrudRepository<Music, Long> {

    List<Music> findAllByContainedListId(Long containedListId);

    Long countMusicByMusicListId(Long listId);
}
