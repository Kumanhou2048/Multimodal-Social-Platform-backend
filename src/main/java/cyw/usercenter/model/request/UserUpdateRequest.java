package cyw.usercenter.model.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    String userAccount;
    String username;
    String gender;
}
