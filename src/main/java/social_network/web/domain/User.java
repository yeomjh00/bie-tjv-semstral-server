package social_network.web.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Set;
import social_network.web.controller.asset.UserRegisterForm;

import java.util.Collection;
import java.util.List;

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
    private List<Post> myPosts;

    @ManyToMany(mappedBy = "likes")
    private List<Post> likedPosts;

    @OneToMany(mappedBy = "owner")
    private List<MusicList> myMusicLists;

    public void setUserStatusTrial(){
        this.setUserStatus("trial");
    }
    public void setUserStatusMembership(){
        this.setUserStatus("membership");
    }

    public boolean equals(User user){
        return this.id.equals(user.getId())
                && this.username.equals(user.getUsername())
                && this.realName.equals(user.getRealName())
                && this.userStatus.equals(user.getUserStatus())
                && this.introduction.equals(user.getIntroduction());
    }
}
