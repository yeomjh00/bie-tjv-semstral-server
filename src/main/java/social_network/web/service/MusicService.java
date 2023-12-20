package social_network.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import social_network.web.domain.Music;
import social_network.web.repository.media.MusicRepository;

import java.util.List;
import java.util.Optional;

public class MusicService implements CrudService<Music, Long>{

    private final MusicRepository musicRepository;

    public MusicService(@Autowired MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    @Override
    public Music save(Music entity) {
        return null;
    }

    @Override
    public Optional<Music> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Music> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
