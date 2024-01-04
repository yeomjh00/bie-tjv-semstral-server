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
    private String title;
    private String artist;

    public static MusicDto Music2Dto(Music music){
        if (music == null) return null;
        return MusicDto.builder()
                .id(music.getId())
                .uri(music.getUri())
                .title(music.getTitle())
                .artist(music.getArtist())
                .build();
    }
}
