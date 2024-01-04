package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<MusicListDto> readAllMusicLists(){
        log.info("read all music lists");
        return musicListService.findAll().stream()
                .map(MusicListDto::MusicList2Dto)
                .toList();
    }

    @PostMapping
    public void createMusicList(@RequestBody MusicListDto musicListDto){
        log.info("create music list: {}", musicListDto);
        musicListService.saveFromDto(musicListDto);
    }

    @GetMapping("/{music-list-id}")
    public MusicListDto readMusicListById(@PathVariable("music-list-id") Long mlId){
        log.info("read music list by id: {}", mlId);
        return MusicListDto.MusicList2Dto(musicListService.findById(mlId).orElse(null));
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
    public List<MusicListDto> readAllMusicListsByOwnerId(@RequestParam Long user_id){
        log.info("read all music lists by owner id: {}", user_id);
        return musicListService.findAllByOwnerId(user_id).stream()
                .map(MusicListDto::MusicList2Dto)
                .toList();
    }

    @GetMapping("/count-list")
    public Long countListsByUserId(@RequestParam Long user_id){
        log.info("count music by music list id: {}", user_id);
        return musicListService.countListByUserId(user_id);
    }

    @GetMapping("/count-music")
    public Long countMusicByMusicListId(@RequestParam Long list_id){
        log.info("count music by music list id: {}", list_id);
        return musicListService.countMusicByMusicListId(list_id);
    }
}
