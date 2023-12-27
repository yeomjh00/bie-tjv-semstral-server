package social_network.web.domain;

import jakarta.persistence.*;
import lombok.*;
import social_network.web.controller.asset.UserDto;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
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

    public static User Dto2User(UserDto userDto){
        String userStatus = "membership".equals(userDto.getUserStatus()) ? "membership" : "trial";
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .realName(userDto.getRealName())
                .userStatus(userStatus)
                .introduction(userDto.getIntroduction())
                .build();
    }

    public void setInfoFromDto(UserDto userDto){
        String userStatus = userDto.getUserStatus().equals("membership") ? "membership" : "trial";
        this.username = userDto.getUsername();
        this.realName = userDto.getRealName();
        this.userStatus = userStatus;
        this.introduction = userDto.getIntroduction();
    }
}
