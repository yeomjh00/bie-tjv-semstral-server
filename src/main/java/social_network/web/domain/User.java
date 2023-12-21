package social_network.web.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Set;
import social_network.web.controller.asset.UserRegisterForm;

import java.util.Collection;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
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
