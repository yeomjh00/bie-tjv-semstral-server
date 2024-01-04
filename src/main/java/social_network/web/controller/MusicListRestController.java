package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_network.web.controller.asset.MusicListDto;
import social_network.web.domain.MusicList;
import social_network.web.domain.User;
import social_network.web.service.MusicListService;
import social_network.web.service.UserService;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/musiclists")
public class MusicListRestController {
  private final UserService userService;
    private final MusicListService musicListService;

    @Autowired
    public MusicListRestController(UserService userService,
                                   MusicListService musicListService){
        this.userService = userService;
        this.musicListService = musicListService;
    }

    @GetMapping
    public ResponseEntity<List<MusicListDto>> readAllMusicLists(){
        log.info("read all music lists");
        List<MusicListDto> lists = musicListService.findAll().stream()
                .map(MusicListDto::MusicList2Dto)
                .toList();
        return ResponseEntity.ok(lists);
    }

    @PostMapping
    public HttpStatus createMusicList(@RequestBody MusicListDto musicListDto){
        log.info("create music list: {}", musicListDto);
        MusicList response = musicListService.saveFromDto(musicListDto);
        if (response == null){
            log.info("music list not created");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.CREATED;
    }

    @GetMapping("/{music-list-id}")
    public ResponseEntity<MusicListDto> readMusicListById(@PathVariable("music-list-id") Long mlId){
        log.info("read music list by id: {}", mlId);
        Optional<MusicList> musicList = musicListService.findById(mlId);
        if (musicList.isEmpty()){
            log.info("music list not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(MusicListDto.MusicList2Dto(musicList.get()));

    }

    @PutMapping("/{music-list-id}")
    public void updateMusicList(@PathVariable("music-list-id") Long mlId,
                                @RequestBody MusicListDto musicListDto){
        log.info("update music list: {}", musicListDto);
        Optional<User> owner = userService.findById(musicListDto.getOwnerId());
        if (owner.isEmpty()){
            log.info("user not found");
            return;
        }
        musicListService.updateFromDto(musicListDto);
    }

    @DeleteMapping("/{music-list-id}")
    public void deleteMusicListById(@PathVariable("music-list-id") Long mlId){
        log.info("delete music list by id: {}", mlId);
        musicListService.deleteById(mlId);
    }

    @GetMapping("/owned")
    public ResponseEntity<List<MusicListDto>> readAllMusicListsByOwnerId(@RequestParam Long user_id){
        log.info("read all music lists by owner id: {}", user_id);
        List<MusicList> lists = musicListService.findAllByOwnerId(user_id);
        if (lists.isEmpty()){
            log.info("music lists not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lists.stream().map(MusicListDto::MusicList2Dto).toList());
    }

    @GetMapping("/count-list")
    public ResponseEntity<Long> countListsByUserId(@RequestParam Long user_id){
        log.info("count music by music list id: {}", user_id);
        Long listCount = musicListService.countListByUserId(user_id);
        if (listCount == null){
            log.info("user not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listCount);
    }

    @GetMapping("/count-music")
    public ResponseEntity<Long> countMusicByMusicListId(@RequestParam Long list_id){
        log.info("count music by music list id: {}", list_id);
        Long musicCount = musicListService.countMusicByMusicListId(list_id);
        if (musicCount == null){
            log.info("music list not found");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(musicCount);
    }
}
