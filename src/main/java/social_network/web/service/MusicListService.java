package social_network.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import social_network.web.domain.MusicList;
import social_network.web.repository.media.MusicListRepository;

import java.util.List;
import java.util.Optional;

public class MusicListService implements CrudService<MusicList, Long>{

    private final MusicListRepository musicListRepository;

    public MusicListService(@Autowired MusicListRepository musicListRepository) {
        this.musicListRepository = musicListRepository;
    }

    @Override
    public MusicList save(MusicList entity) {
        return null;
    }

    @Override
    public Optional<MusicList> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<MusicList> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
