package cyw.usercenter.model.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 *
 * @TableName user_follows
 */
@TableName(value ="user_follows")
@Data
public class UserFollows {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 关注者id
     */
    private String follower_id;

    /**
     * 被关注者id
     */
    private String following_id;

    /**
     * 关注时间
     */
    private LocalDateTime create_time;
}
