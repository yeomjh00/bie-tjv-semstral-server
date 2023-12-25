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
        Long id = user == null ? -1 : user.getId();
        return UserDto.builder()
                .id(id)
                .username(user.getUsername())
                .realName(user.getRealName())
                .userStatus(user.getUserStatus())
                .introduction(user.getIntroduction())
                .build();
    }
}
