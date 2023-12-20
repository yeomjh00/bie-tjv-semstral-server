package social_network.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import social_network.web.domain.Picture;
import social_network.web.repository.media.PictureRepository;

import java.util.List;
import java.util.Optional;

public class PictureService implements CrudService<Picture, Long>{

    private final PictureRepository pictureRepository;

    public PictureService(@Autowired PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture save(Picture entity) {
        return null;
    }

    @Override
    public Optional<Picture> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Picture> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
