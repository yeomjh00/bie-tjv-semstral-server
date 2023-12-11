package social_network.web.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@DiscriminatorValue("playable")
@Getter @Setter
public class PlayableMedia extends Media{
    private String title;
    private String artist;
    private Long postedTimes ;
    @ManyToMany
    private Collection<MusicList> containedLists;
}
