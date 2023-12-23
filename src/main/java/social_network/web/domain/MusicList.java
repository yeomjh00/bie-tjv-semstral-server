package social_network.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter
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
    private List<Music> track;

    public boolean equals(MusicList musicList){
        return this.id.equals(musicList.getId())
                && this.owner.equals(musicList.getOwner())
                && this.listName.equals(musicList.getListName())
                && this.description.equals(musicList.getDescription());
    }
}
