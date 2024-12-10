package cyw.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateRequest implements Serializable {
    private static final long serialVersionUID = 4L;
    String userAccount;
    String username;
    String gender;
}
