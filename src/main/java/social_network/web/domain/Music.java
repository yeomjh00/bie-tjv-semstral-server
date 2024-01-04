package social_network.web.domain;

import jakarta.persistence.*;
import lombok.*;
import social_network.web.controller.asset.MusicDto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "music")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Music{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicId")
    private Long id;
    private String uri;
    private String title;
    private String artist;
    @OneToMany(mappedBy = "song", cascade = CascadeType.MERGE)
    private List<Post> containedPosts;
    @ManyToMany(mappedBy = "track", cascade = CascadeType.MERGE)
    private Collection<MusicList> containedLists;

    public static Music Dto2Music(MusicDto musicDto){
        return Music.builder()
                .id(musicDto.getId())
                .uri(musicDto.getUri())
                .title(musicDto.getTitle())
                .artist(musicDto.getArtist())
                .build();
    }

    public boolean equals(Music music){
        return this.id.equals(music.getId())
                && this.uri.equals(music.getUri())
                && this.title.equals(music.getTitle())
                && this.artist.equals(music.getArtist());
    }
}
