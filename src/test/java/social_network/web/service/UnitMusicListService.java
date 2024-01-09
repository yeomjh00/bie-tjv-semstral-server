package social_network.web.service;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import social_network.web.controller.asset.MusicDto;
import social_network.web.controller.asset.MusicListDto;
import social_network.web.domain.Music;
import social_network.web.domain.MusicList;
import social_network.web.domain.User;
import social_network.web.repository.UserRepository;
import social_network.web.repository.media.MusicListRepository;
import social_network.web.repository.media.MusicRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnitMusicListService {

    @InjectMocks
    MusicListService musicListService;

    @Mock
    MusicListRepository musicListRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    MusicRepository musicRepository;

    static User trialUser = User.builder().id(1L).username("user1").realName("user1").userStatus("trial").build();
    static User membershipUser = User.builder().id(2L).username("user2").realName("user2").userStatus("membership").build();
    static List<MusicList> trialLists = List.of(new MusicList[]{
            MusicList.builder().listName("listName").description("description").owner(trialUser).track(Collections.emptyList()).build(),
            MusicList.builder().listName("listName").description("description").owner(trialUser).track(Collections.emptyList()).build(),
            MusicList.builder().listName("listName").description("description").owner(trialUser).track(Collections.emptyList()).build(),
    });
    static List<MusicList> membershipLists = List.of(new MusicList[]{
            MusicList.builder().listName("listName").description("description").owner(membershipUser).track(Collections.emptyList()).build(),
            MusicList.builder().listName("listName").description("description").owner(membershipUser).track(Collections.emptyList()).build(),
            MusicList.builder().listName("listName").description("description").owner(membershipUser).track(Collections.emptyList()).build(),
    });

    static List<Music> musics = List.of(new Music[]{
            Music.builder().id(1L).title("title").artist("artist").uri("uri1").build(),
            Music.builder().id(2L).title("title").artist("artist").uri("uri2").build(),
            Music.builder().id(3L).title("title").artist("artist").uri("uri3").build(),
    });

    @BeforeEach
    void init(){
        // User
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
        when(userRepository.findById(trialUser.getId())).thenReturn(Optional.of(trialUser));
        when(userRepository.findById(membershipUser.getId())).thenReturn(Optional.of(membershipUser));

        // musics
        when(musicRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());
        for(var music : musics){
            when(musicRepository.findById(music.getId())).thenReturn(Optional.of(music));
        }
    }


    @AfterEach
    void tearDown() {
        reset(musicListRepository);
        reset(userRepository);
        reset(musicRepository);
    }


    @Test
    void saveFromDto_userNotFound() {
        // given
        Long invalidId = 0L;

        // when
        MusicList musicList = musicListService.saveFromDto(MusicListDto.builder().ownerId(invalidId).build());

        // then
        assertThat(musicList).isEqualTo(null);
    }

    @Test
    void saveFromDto_invalidListName() {
        // given
        MusicListDto invalidName = MusicListDto.builder().listName("").description("description").ownerId(1L).build();

        // when
        MusicList musicList = musicListService.saveFromDto(invalidName);

        // then
        assertThat(musicList).isEqualTo(null);
    }
    @Test
    void saveFromDto_invalidDescription() {
        // given
        MusicListDto invalidDescription = MusicListDto.builder().listName("listName").description("").ownerId(1L).build();


        // when
        MusicList musicList = musicListService.saveFromDto(invalidDescription);

        // then
        assertThat(musicList).isEqualTo(null);
    }

    @Test
    void addMusicsToList() {
        //given
        MusicList musicList = MusicList.builder().listName("listName").description("description").owner(trialUser).track(new ArrayList<>()).build();

        //when
        musicListService.addMusicsToList(musicList, List.of(1L, 2L, 3L));

        //then
        assertThat(musicList.getTrack()).usingRecursiveComparison().isEqualTo(musics);
    }

    @Test
    void deleteMusicsInList() {
        //given
        MusicList musicList = MusicList.builder().listName("listName").description("description").owner(trialUser).track(new ArrayList<>(musics)).build();

        //when
        musicListService.deleteMusicsInList(musicList, List.of(1L, 2L, 3L));

        //then
        assertThat(musicList.getTrack()).isEqualTo(Collections.emptyList());
    }
}