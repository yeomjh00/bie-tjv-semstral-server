package social_network.web.domain;

import jakarta.persistence.*;
import lombok.*;
import social_network.web.controller.asset.MusicDto;
import social_network.web.controller.asset.MusicListDto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class MusicList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listId")
    private Long id;
    @ManyToOne
    private User owner;
    private String listName;
    private String description;
    @ManyToMany
    private List<Music> track;

    public static MusicList Dto2MusicList(MusicListDto musicListDto, User owner){
        List<Music> track = musicListDto.getTrack() == null ?
                Collections.emptyList() : musicListDto.getTrack().stream().map(Music::Dto2Music).toList();

        return MusicList.builder()
                .id(musicListDto.getId())
                .owner(owner)
                .listName(musicListDto.getListName())
                .description(musicListDto.getDescription())
                .track(track)
                .build();
    }

    public boolean equals(MusicList musicList){
        return this.id.equals(musicList.getId())
                && this.owner.equals(musicList.getOwner())
                && this.listName.equals(musicList.getListName())
                && this.description.equals(musicList.getDescription());
    }
}
