package com.carbon.assets.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 碳元注册项目表实体类
 */
@Data
@TableName("carbon_metaregistry_project")
public class CarbonMetaregistryProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long metaregistryId;

    private Long tenantId;

    private String projectIntroduction;

}

