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

    public static UserDto User2ShortDto(User user){
        if (user == null) return userNotFound();
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .realName(user.getRealName())
                .userStatus(user.getUserStatus())
                .introduction("")
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
}
