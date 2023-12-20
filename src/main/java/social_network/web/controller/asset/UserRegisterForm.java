package social_network.web.controller.asset;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRegisterForm {
    private String username;
    private String realName;
    private String introduction;
    private String userStatus;
}