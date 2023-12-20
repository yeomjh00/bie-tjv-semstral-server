package social_network.web.domain;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
public class MusicList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "listId")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;
    private String listName;
    private String description;
    @ManyToMany(mappedBy = "containedLists")
    private Collection<Music> track;
}
