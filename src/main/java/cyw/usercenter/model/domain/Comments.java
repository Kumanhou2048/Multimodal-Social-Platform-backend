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
 * @TableName comments
 */
@TableName(value ="comments")
@Data
public class Comments implements Serializable {
    /**
     * 评论编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 笔记编号
     */
    private Integer noteId;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 评论时间
     */
    private Date uploadTime;

    /**
     * 评论正文
     */
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}