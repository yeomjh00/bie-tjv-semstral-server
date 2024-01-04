package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social_network.web.controller.asset.PictureDto;
import social_network.web.domain.Picture;
import social_network.web.service.PictureService;
import social_network.web.service.PostService;
import social_network.web.service.UserService;

import java.util.List;

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
    public List<PictureDto> readAllPictures(){
        log.info("read all pictures");
        return pictureService.findAll().stream()
                .map(PictureDto::Picture2Dto)
                .toList();
    }

    @PostMapping
    public void createPicture(@RequestBody PictureDto pictureDto){
        log.info("create picture: {}", pictureDto);
        pictureService.save(Picture.Dto2Picture(pictureDto));
    }

    @GetMapping("/{picture-id}")
    public PictureDto readPictureById(@PathVariable("picture-id") Long pId){
        log.info("read picture by id: {}", pId);
        return PictureDto.Picture2Dto(pictureService.findById(pId).orElse(null));
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
    public List<PictureDto> readAllPicturesByContainedPostId(@RequestParam Long post_id){
        log.info("read all pictures by contained post id: {}", post_id);
        return pictureService.findAllByPostId(post_id).stream()
                .map(PictureDto::Picture2Dto)
                .toList();
    }
}
