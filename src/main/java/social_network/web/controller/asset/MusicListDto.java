package social_network.web.controller.asset;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import social_network.web.domain.Music;
import social_network.web.domain.MusicList;
import social_network.web.domain.User;

import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class MusicListDto {
    private Long id;
    private String listName;
    private String ownerUsername;
    private Long ownerId;
    private String description;
    private List<MusicDto> track;

    public static MusicListDto MusicList2Dto(MusicList musicList){
        if (musicList == null) return null;
        return MusicListDto.builder()
                .id(musicList.getId())
                .listName(musicList.getListName())
                .ownerUsername(musicList.getOwner().getUsername())
                .ownerId(musicList.getOwner().getId())
                .description(musicList.getDescription())
                .track(musicList.getTrack().stream().map(MusicDto::Music2Dto).toList())
                .build();
    }

    public static MusicListDto MusicList2ShortDto(MusicList musicList){
        return MusicListDto.builder()
                .id(musicList.getId())
                .listName(musicList.getListName())
                .ownerUsername(musicList.getOwner().getUsername())
                .ownerId(musicList.getOwner().getId())
                .description(musicList.getDescription())
                .track(null)
                .build();
    }
}