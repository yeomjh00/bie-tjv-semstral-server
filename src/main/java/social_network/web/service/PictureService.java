package social_network.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social_network.web.controller.asset.PictureDto;
import social_network.web.domain.Picture;
import social_network.web.repository.media.PictureRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PictureService implements CrudService<Picture, Long>{

    private final PictureRepository pictureRepository;

    public PictureService(@Autowired PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture save(Picture entity) {
        return pictureRepository.save(entity);
    }

    @Override
    public Optional<Picture> findById(Long aLong) {
        return pictureRepository.findById(aLong);
    }

    @Override
    public List<Picture> findAll() {
        return pictureRepository.findAll();
    }

    @Override
    public void deleteById(Long aLong) {
        pictureRepository.deleteById(aLong);
    }

    public List<Picture> findAllByPostId(Long postId) {
        return pictureRepository.findAllByPostId(postId);
    }

    public void saveAllFromDto(List<PictureDto> pictureDtos) {
        List<Picture> photos = pictureDtos.stream().map(Picture::Dto2Picture).toList();
        for (Picture photo: photos){
            pictureRepository.save(photo);
        }
    }

    public List<Picture> saveAll(List<Picture> pictures) {
        for (Picture photo: pictures){
            pictureRepository.save(photo);
        }
        return pictures;
    }

    public void deletePictures(List<Picture> pictures) {
        for (Picture photo : pictures){
            pictureRepository.deleteById(photo.getId());
        }
    }
}
