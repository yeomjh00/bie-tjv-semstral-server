package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_network.web.controller.asset.PictureDto;
import social_network.web.domain.Picture;
import social_network.web.service.PictureService;
import social_network.web.service.PostService;
import social_network.web.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/pictures")
public class PictureRestController {
    private final PostService postService;
    private final PictureService pictureService;

    @Autowired
    public PictureRestController(PostService postService,
                                 PictureService pictureService){
        this.postService = postService;
        this.pictureService = pictureService;
    }

    @GetMapping
    public ResponseEntity<List<PictureDto>> readAllPictures(){
        log.info("read all pictures");
        List<PictureDto> pictures = pictureService.findAll().stream()
                .map(PictureDto::Picture2Dto)
                .toList();
        return ResponseEntity.ok(pictures);
    }

    @PostMapping
    public HttpStatus createPicture(@RequestBody PictureDto pictureDto){
        log.info("create picture: {}", pictureDto);
        Picture response = pictureService.save(Picture.Dto2Picture(pictureDto));
        if (response == null){
            log.info("picture not created");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.CREATED;
    }

    @GetMapping("/{picture-id}")
    public ResponseEntity<PictureDto> readPictureById(@PathVariable("picture-id") Long pId){
        log.info("read picture by id: {}", pId);
        Optional<Picture> picture = pictureService.findById(pId);
        if (picture.isEmpty()){
            log.info("picture not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(PictureDto.Picture2Dto(picture.get()));
    }

    @PutMapping("/{picture-id}")
    public void updatePicture(@PathVariable("picture-id") Long pId,
                              @RequestBody PictureDto pictureDto){
        log.info("update picture: {}", pictureDto);
        pictureService.save(Picture.Dto2Picture(pictureDto));
    }

    @DeleteMapping("/{picture-id}")
    public void deletePictureById(@PathVariable("picture-id") Long pId){
        log.info("delete picture by id: {}", pId);
        pictureService.deleteById(pId);
    }

    @GetMapping("/contained")
    public ResponseEntity<List<PictureDto>> readAllPicturesByContainedPostId(@RequestParam Long post_id){
        log.info("read all pictures by contained post id: {}", post_id);
        List<Picture> pictures = pictureService.findAllByPostId(post_id);
        if (pictures.isEmpty()){
            log.info("pictures not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pictures.stream().map(PictureDto::Picture2Dto).toList());
    }
}
