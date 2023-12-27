package social_network.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/{user_id}/music_list")
public class MusicListRestController {
//  private final UserService userService;
//    private final MusicListService musicListService;
//
//    @Autowired
//    public MusicListRestController(UserService userService,
//    MusicListService musicListService){
//        this.userService = userService;
//        this.musicListService = musicListService;
//    }
//
//    @GetMapping
//    public List<MusicListDto> readAllMusicLists(@PathVariable Long user_id){
//        log.info("read all music lists");
//        return musicListService.findAll().stream()
//                .map(MusicListDto::MusicList2Dto)
//                .toList();
//    }

}
