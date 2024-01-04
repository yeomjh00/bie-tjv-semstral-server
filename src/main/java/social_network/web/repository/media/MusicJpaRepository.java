package social_network.web.repository.media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_network.web.domain.Music;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicJpaRepository extends JpaRepository<Music, Long>, MusicRepository {

    @Override
    @Query("SELECT m FROM Music m JOIN m.containedLists ml WHERE ml.id = :listId")
    List<Music> findAllByContainedListId(@Param("listId") Long containedListId);

    @Override
    @Query("SELECT COUNT(m) FROM Music m JOIN m.containedLists ml WHERE ml.id = :listId")
    Long countMusicByMusicListId(@Param("listId") Long listId);
}
