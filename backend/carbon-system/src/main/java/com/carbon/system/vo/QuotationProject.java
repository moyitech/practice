package com.carbon.system.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * 报价项目VO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotationProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    private String type;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 签约数量
     */
    private Integer singCount;

    /**
     * 库存数量
     */
    private Integer stockCount;
}





