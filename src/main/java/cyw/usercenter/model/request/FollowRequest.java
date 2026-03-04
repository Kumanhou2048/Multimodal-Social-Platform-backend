package cyw.usercenter.model.request;

import lombok.Data;

@Data
public class FollowRequest {
    private String followerAccount;  // 当前登录用户
    private String followingAccount; // 要关注的目标用户
}
