package social_network.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "music")
@Getter @Setter
public class Music{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicId")
    private Long id;
    private String uri;
    private String description;
    private String title;
    private String artist;
    private Long playDuration;
    @ManyToMany
    private Collection<MusicList> containedLists;

    public boolean equals(Music music){
        return this.id.equals(music.getId())
                && this.uri.equals(music.getUri())
                && this.description.equals(music.getDescription())
                && this.title.equals(music.getTitle())
                && this.artist.equals(music.getArtist())
                && this.playDuration.equals(music.getPlayDuration());
    }
}
