package social_network.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social_network.web.controller.asset.MusicDto;
import social_network.web.domain.Music;
import social_network.web.repository.media.MusicRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MusicService implements CrudService<Music, Long>{

    private final MusicRepository musicRepository;

    public MusicService(@Autowired MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public Music save(Music entity) {
        return musicRepository.save(entity);
    }

    @Override
    public Optional<Music> findById(Long musicId) {
        return musicRepository.findById(musicId);
    }

    @Override
    public List<Music> findAll() {
        return musicRepository.findAll();
    }

    @Override
    public void deleteById(Long musicId) {
        musicRepository.deleteById(musicId);
    }

    public List<Music> findAllByContainedListId(Long containedListId) {
        return musicRepository.findAllByContainedListId(containedListId);
    }

    public Long countMusicByMusicListId(Long listId) {
        return musicRepository.countMusicByMusicListId(listId);
    }

    public void saveFromDto(MusicDto musicDto) {
        var music = Music.Dto2Music(musicDto);
        musicRepository.save(music);
    }
}
