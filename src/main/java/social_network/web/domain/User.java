package social_network.web.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.Set;

import java.util.Collection;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long id;
    private String username;
    private String realName;
    private String userStatus;
    private String introduction;
    @OneToMany(mappedBy = "author")
    private Collection<Post> myPosts;

    @ManyToMany(mappedBy = "likes")
    private Collection<Post> likedByMe;

    @OneToMany(mappedBy = "owner")
    private Collection<MusicList> myMusicLists;

    public void setUserStatusTrial(){
        this.setUserStatus("trial");
    }
    public void setUserStatusMembership(){
        this.setUserStatus("membership");
    }
}
