package com.carbon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 碳文章实体类
 */
@Data
@TableName("carbon_article")
public class CarbonArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 分类ID（1-动态，2-知识库，3-公告，4-常见问题）
     */
    private String categoryId;

    /**
     * 作者
     */
    private String author;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否发布（0-未发布，1-已发布）
     */
    private Integer published;
}

