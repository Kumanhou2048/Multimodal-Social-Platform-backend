package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName images
 */
@TableName(value ="images")
@Data
public class Images implements Serializable {
    /**
     * 图片编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 笔记编号
     */
    private Integer noteId;

    /**
     * 图片顺序
     */
    private Integer imageIndex;

    /**
     * 图片路径
     */
    private String imageUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}