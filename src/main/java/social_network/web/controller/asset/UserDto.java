package social_network.web.controller.asset;

import lombok.*;
import social_network.web.domain.User;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class UserDto {
    private Long id;
    private String username;
    private String realName;
    private String userStatus;
    private String introduction;

    public static UserDto User2Dto(User user){
        if (user == null) return userNotFound();
        String safeIntroduction = user.getIntroduction() == null ? "" : user.getIntroduction();
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .realName(user.getRealName())
                .userStatus(user.getUserStatus())
                .introduction(safeIntroduction)
                .build();
    }

    public static UserDto userNotFound(){
        return UserDto.builder()
                .id(-1L)
                .username("User Not Found")
                .realName("User Not Found")
                .userStatus("User Not Found")
                .introduction("User Not Found")
                .build();
    }

    public static UserDto duplicatedUserName(){
        return UserDto.builder()
                .id(-1L)
                .username("Duplicated User Name")
                .realName("Duplicated User Name")
                .userStatus("Duplicated User Name")
                .introduction("Duplicated User Name")
                .build();
    }

    public static UserDto invalidName(){
        return UserDto.builder()
                .id(-1L)
                .username("Invalid Name")
                .realName("Invalid Name")
                .userStatus("Invalid Name")
                .introduction("Invalid Name")
                .build();
    }

    public boolean equals(UserDto user){
        return this.id.equals(user.getId())
                && this.username.equals(user.getUsername())
                && this.realName.equals(user.getRealName())
                && this.userStatus.equals(user.getUserStatus())
                && this.introduction.equals(user.getIntroduction());
    }
}
