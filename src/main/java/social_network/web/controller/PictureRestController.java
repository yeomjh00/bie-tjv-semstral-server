package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_network.web.controller.asset.PictureDto;
import social_network.web.controller.asset.UserDto;
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
    private final PictureService pictureService;

    @Autowired
    public PictureRestController(PictureService pictureService){
        this.pictureService = pictureService;
    }

    @GetMapping
    public ResponseEntity<List<PictureDto>> readAllPictures(){
        log.info("read all pictures");
        List<PictureDto> response = pictureService.findAllPicturesFromDto();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public HttpStatus createPicture(@RequestBody PictureDto pictureDto){
        return pictureService.createPicture(pictureDto);
    }

    @GetMapping("/{picture-id}")
    public ResponseEntity<PictureDto> readPictureById(@PathVariable("picture-id") Long pId){
        return pictureService.readPictureById(pId);
    }

    @PutMapping("/{picture-id}")
    public HttpStatus updatePicture(@PathVariable("picture-id") Long pId,
                              @RequestBody PictureDto pictureDto){
        return pictureService.updatePictureById(pId, pictureDto);
    }

    @DeleteMapping("/{picture-id}")
    public void deletePictureById(@PathVariable("picture-id") Long pId){
        log.info("delete picture by id: {}", pId);
        pictureService.deleteById(pId);
    }
}
