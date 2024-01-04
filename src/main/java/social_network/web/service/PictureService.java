package social_network.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public HttpStatus createPicture(PictureDto pictureDto){
        log.info("create picture: {}", pictureDto);
        Picture response = pictureRepository.save(Picture.Dto2Picture(pictureDto));
        if (response == null){
            log.info("picture not created");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.CREATED;
    }

    @Override
    public Optional<Picture> findById(Long aLong) {
        return pictureRepository.findById(aLong);
    }

    public ResponseEntity<PictureDto> readPictureById(Long pId){
        log.info("read picture by id: {}", pId);
        Optional<Picture> picture = findById(pId);
        if (picture.isEmpty()){
            log.info("picture not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(PictureDto.Picture2Dto(picture.get()));
    }

    @Override
    public List<Picture> findAll() {
        return pictureRepository.findAll();
    }

    public List<PictureDto> findAllPicturesFromDto() {
        return pictureRepository.findAll().stream()
                .map(PictureDto::Picture2Dto)
                .toList();
    }

    public HttpStatus updatePictureById(Long pId, PictureDto pictureDto){
        Optional<Picture> photo = findById(pId);
        if (photo.isEmpty()){
            log.info("picture not found");
            return HttpStatus.NOT_FOUND;
        }
        log.info("update picture: {}", pictureDto);
        Picture p = photo.get();
        p.setUri(pictureDto.getUri());
        p.setHeight(pictureDto.getHeight());
        p.setWidth(pictureDto.getWidth());
        save(p);
        return HttpStatus.OK;
    }

    @Override
    public void deleteById(Long aLong) {
        pictureRepository.deleteById(aLong);
    }

    public List<Picture> findAllByPostId(Long postId) {
        return pictureRepository.findAllByPostId(postId);
    }


}
