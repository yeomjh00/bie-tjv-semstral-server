package social_network.web.controller.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserRegisterForm {
    private String username;
    private String realName;
    private String introduction;
    private String userStatus;
}