package cyw.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName likes
 */
@TableName(value ="likes")
@Data
public class Likes implements Serializable {
    /**
     * 点赞编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 笔记编号
     */
    private Integer noteId;

    /**
     * 点赞者编号
     */
    private Integer userId;

    /**
     * 点赞时间
     */
    private Date uploadTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}