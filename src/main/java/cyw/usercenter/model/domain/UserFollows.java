package cyw.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user_follows")
public class UserFollows {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 必须加这个注解！告知数据库列名带下划线
    @TableField("follower_account")
    private String followerAccount;

    // 必须加这个注解！告知数据库列名带下划线
    @TableField("following_account")
    private String followingAccount;

    @TableField("create_time")
    private LocalDateTime createTime;
}