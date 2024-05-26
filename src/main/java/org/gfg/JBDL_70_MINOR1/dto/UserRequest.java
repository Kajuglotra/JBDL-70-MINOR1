package org.gfg.JBDL_70_MINOR1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.gfg.JBDL_70_MINOR1.model.User;
import org.gfg.JBDL_70_MINOR1.model.UserStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRequest {

    private String userName;

    @NotBlank(message = "user phoneNo should not be blank")
    private String phoneNo;

    private String email;

    private String  address;

    public User toUser() {
        return User.
                builder().
                name(this.userName).
                email(this.email).
                phoneNo(this.phoneNo).
                address(this.address).
                userStatus(UserStatus.ACTIVE).
                build();
    }
}
