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
 * @TableName notes
 */

//其中noteType 0代表图文，1代表视频
//noteStatus 0代表待审核，1代表审核通过

@TableName(value ="notes")
@Data
public class Notes implements Serializable {
    /**
     * 笔记编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片数量
     */
    private Integer imageCount;

    /**
     * 笔记类型
     */
    private Integer noteType;

    /**
     * 笔记状态
     */
    private Integer noteStatus;

    /**
     * 点赞数
     */
    private Integer likes;

    /**
     * 收藏数
     */
    private Integer collection;

    /**
     * 评论数
     */
    private Integer comments;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}