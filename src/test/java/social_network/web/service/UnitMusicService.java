package social_network.web.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import social_network.web.controller.asset.MusicDto;
import social_network.web.domain.Music;
import social_network.web.repository.media.MusicRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnitMusicService {

    @InjectMocks
    MusicService musicService;

    @Mock
    MusicRepository musicRepository;

    static List<Music> musics = List.of(new Music[]{
            Music.builder().id(1L).uri("uri1").containedPosts(Collections.emptyList()).containedLists(Collections.emptyList()).build(),
    });

    @AfterEach
    void tearDown(){
        reset(musicRepository);
    }

    @Test
    void saveFromDto_valid() {
        //given
        MusicDto musicDto = MusicDto.Music2Dto(Music.builder().id(1L).uri("uri1").containedPosts(Collections.emptyList()).containedLists(Collections.emptyList()).build());

        //when
        when(musicRepository.save(any(Music.class))).thenReturn(Music.Dto2Music(musicDto));
        var result = musicService.saveFromDto(musicDto);

        //then
        assertEquals(result, HttpStatus.CREATED);
    }

    @Test
    void saveFromDto_invalid() {
        //given
        MusicDto musicDto = MusicDto.Music2Dto(Music.builder().id(1L).uri("a".repeat(1024)).containedPosts(Collections.emptyList()).containedLists(Collections.emptyList()).build());

        //when
        var result = musicService.saveFromDto(musicDto);

        //then
        assertEquals(result, HttpStatus.BAD_REQUEST);
    }

    @Test
    void saveFromDto_null() {
        //given
        MusicDto musicDto = null;

        //when
        var result = musicService.saveFromDto(musicDto);

        //then
        assertEquals(result, HttpStatus.BAD_REQUEST);
    }

    @Test
    void saveFromDto_empty(){
        //given
        MusicDto musicDto = MusicDto.Music2Dto(Music.builder().id(1L).uri("").containedPosts(Collections.emptyList()).containedLists(Collections.emptyList()).build());

        //when
        var result = musicService.saveFromDto(musicDto);

        //then
        assertEquals(result, HttpStatus.BAD_REQUEST);
    }
}