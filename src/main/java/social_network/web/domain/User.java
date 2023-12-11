package social_network.web.domain;

import jakarta.persistence.*;
import org.hibernate.mapping.Set;

import java.util.Collection;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String username;
    private String realName;
    @OneToMany(mappedBy = "author")
    private Collection<Post> myPosts;

    @ManyToMany(mappedBy = "likes")
    private Collection<Post> likedByMe;

    @OneToMany(mappedBy = "creator")
    private Collection<MusicList> myMusicLists;
}
