package com.carbon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.carbon.system.vo.StatHomeDataVo;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据面板实体类
 */
@Data
@TableName(value = "data_panel", autoResultMap = true)
public class DataPanel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 首页数据（JSON格式）
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private StatHomeDataVo homeData;

    /**
     * 所属租户ID
     */
    private Long tenantId;
}





