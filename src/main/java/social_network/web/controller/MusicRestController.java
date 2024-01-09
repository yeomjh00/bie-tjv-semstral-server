package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final MusicService musicService;

    @Autowired
    public MusicRestController(MusicService musicService){
        this.musicService = musicService;
    }

    @GetMapping
    public ResponseEntity<List<MusicDto>> readAllMusics(){
        log.info("read all musics");
        List<MusicDto> musics = musicService.findAll().stream()
                .map(MusicDto::Music2Dto)
                .toList();
        return ResponseEntity.ok(musics);
    }

    @PostMapping
    public HttpStatus createMusic(@RequestBody MusicDto musicDto){
        log.info("create music: {}", musicDto);
        return musicService.saveFromDto(musicDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicDto> readMusicById(@PathVariable Long id){
        log.info("read music by id: {}", id);
        Optional<Music> music = musicService.findById(id);
        if (music.isEmpty()){
            log.info("music not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MusicDto.Music2Dto(music.get()));
    }

    @GetMapping("/contained")
    public ResponseEntity<List<MusicDto>> readAllMusicsByContainedListId(@RequestParam Long listId){
        log.info("read all musics by contained list id: {}", listId);
        List<Music> musics = musicService.findAllByContainedListId(listId);
        if (musics == null){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else if (musics.isEmpty()){
            log.info("musics not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(musics.stream().map(MusicDto::Music2Dto).toList());
    }
}
