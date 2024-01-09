package social_network.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import social_network.web.controller.asset.MusicDto;
import social_network.web.controller.asset.MusicListDto;
import social_network.web.domain.Music;
import social_network.web.domain.MusicList;
import social_network.web.domain.User;
import social_network.web.repository.UserRepository;
import social_network.web.repository.media.MusicListRepository;
import social_network.web.repository.media.MusicRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MusicListService implements CrudService<MusicList, Long>{

    private final UserRepository userRepository;
    private final MusicListRepository musicListRepository;
    private final MusicRepository musicRepository;

    @Autowired
    public MusicListService(MusicListRepository musicListRepository,
                            MusicRepository musicRepository,
                            UserRepository userRepository) {
        this.musicListRepository = musicListRepository;
        this.musicRepository = musicRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MusicList save(MusicList entity) {
        return musicListRepository.save(entity);
    }

    public MusicList saveFromDto(MusicListDto musicListDto) {
        Optional<User> user = userRepository.findById(musicListDto.getOwnerId());
        if (user.isEmpty()){
            log.info("user not found");
            return null;
        } else if (musicListDto.getListName() == null
                || musicListDto.getListName().isEmpty()
                || musicListDto.getListName().length() > 255){
            log.info("list name is empty");
            return null;
        } else if(musicListDto.getDescription() == null
                || musicListDto.getDescription().isEmpty()
                || musicListDto.getDescription().length() > 255){
            log.info("description is empty");
            return null;
        }
        User owner = user.get();
        return musicListRepository.save(MusicList.Dto2MusicList(musicListDto, owner));
    }

    @Override
    public Optional<MusicList> findById(Long mlId) {
        return musicListRepository.findById(mlId);
    }

    @Override
    public List<MusicList> findAll() {
        return musicListRepository.findAll();
    }

    public List<MusicList> findAllByOwnerId(Long userId) {
        return musicListRepository.findAllByOwnerId(userId);
    }

    @Override
    public void deleteById(Long aLong) {
        musicListRepository.deleteById(aLong);
    }

    public Long countListByUserId(Long ownerId) {
        return musicListRepository.countListByUserId(ownerId);
    }

    public Long countMusicByMusicListId(Long listId) {
        return musicRepository.countMusicByMusicListId(listId);
    }


    public void addMusicsToList(MusicList list, List<Long> musicIds) {
        musicIds.forEach(musicId -> {
            musicRepository.findById(musicId).ifPresent(music -> {
                list.getTrack().add(music);
                musicListRepository.save(list);
            });
        });
    }

    public void deleteMusicsInList(MusicList list, List<Long> musicIds) {
        musicIds.forEach(musicId -> {
            musicRepository.findById(musicId).ifPresent(music -> {
                list.getTrack().remove(music);
                musicListRepository.save(list);
            });
        });
    }

    public void updateFromDto(MusicListDto musicListDto) {
        Optional<MusicList> musicList = musicListRepository.findById(musicListDto.getId());
        if (musicList.isEmpty()){
            log.info("music list not found");
            return;
        }
        MusicList list = musicList.get();
        deleteMusicsInList(list, list.getTrack().stream().map(Music::getId).toList());
        addMusicsToList(list, musicListDto.getTrack().stream().map(
                MusicDto::getId).toList());
    }
}
