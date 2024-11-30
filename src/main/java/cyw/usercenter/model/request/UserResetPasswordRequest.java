package cyw.usercenter.model.request;

import lombok.Data;

@Data
public class UserResetPasswordRequest {
    private static final long serialVersionUID = 3L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
