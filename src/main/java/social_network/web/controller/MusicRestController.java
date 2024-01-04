package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import social_network.web.controller.asset.MusicDto;
import social_network.web.domain.Music;
import social_network.web.service.MusicService;
import social_network.web.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/musics")
public class MusicRestController {
    private final UserService userService;
    private final MusicService musicService;

    @Autowired
    public MusicRestController(UserService userService,
                               MusicService musicService){
        this.userService = userService;
        this.musicService = musicService;
    }

    @GetMapping
    List<MusicDto> readAllMusics(){
        log.info("read all musics");
        return musicService.findAll().stream().map(MusicDto::Music2Dto).toList();
    }

//    @GetMapping
//    Optional<MusicDto> readMusicByPostId(@RequestParam Long postId){
//        log.info("read music by post id: {}", postId);
//        return musicService.findBy(postId).map(MusicDto::Music2Dto);
//    }

    @PostMapping
    void createMusic(@RequestBody MusicDto musicDto){
        log.info("create music: {}", musicDto);
        musicService.saveFromDto(musicDto);
    }

    @GetMapping("/{id}")
    MusicDto readMusicById(@PathVariable Long id){
        log.info("read music by id: {}", id);
        return musicService.findById(id).map(MusicDto::Music2Dto).orElse(null);
    }

    @GetMapping("/contained")
    List<MusicDto> readAllMusicsByContainedListId(@RequestParam Long listId){
        log.info("read all musics by contained list id: {}", listId);
        return musicService.findAllByContainedListId(listId).stream().map(MusicDto::Music2Dto).toList();
    }
}
