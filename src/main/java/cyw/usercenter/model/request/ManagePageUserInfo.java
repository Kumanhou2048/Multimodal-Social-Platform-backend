package cyw.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ManagePageUserInfo implements Serializable {
    private static final long serialVersionUID = 12L;
    private String userName;
    private String avatarUrl;
    private String error;
}
