package cyw.usercenter.model.request;

import lombok.Data;

public class FollowRequest {
    private String followerAccount;  // 当前登录用户
    private String followingAccount; // 要关注的目标用户
    private boolean isFollow;        // true: 关注, false: 取关
}
