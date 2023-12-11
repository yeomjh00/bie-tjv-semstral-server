package social_network.web.domain;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class MusicList {
    @Id
    private Long id;
    @ManyToOne
    private User creator;
    private String listName;
    private String description;
    @ManyToMany(mappedBy = "containedLists")
    private Collection<PlayableMedia> musicList;
}
