package social_network.web.controller.asset;

import jakarta.persistence.ManyToMany;
import lombok.*;
import social_network.web.domain.Music;
import social_network.web.domain.MusicList;

import java.util.Collection;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class MusicDto {
    private Long id;
    private String uri;
    private String description;
    private String title;
    private String artist;
    private Long playDuration;

    public static MusicDto Music2Dto(Music music){
        return MusicDto.builder()
                .id(music.getId())
                .uri(music.getUri())
                .description(music.getDescription())
                .title(music.getTitle())
                .artist(music.getArtist())
                .playDuration(music.getPlayDuration())
                .build();
    }
}
