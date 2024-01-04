package social_network.web.repository.media;

import social_network.web.domain.Picture;
import social_network.web.repository.CrudRepository;

import java.util.List;

public interface PictureRepository extends CrudRepository<Picture, Long> {

    List<Picture> findAllByPostId(Long postId);
}
