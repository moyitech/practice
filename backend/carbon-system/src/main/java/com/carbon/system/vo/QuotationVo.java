package com.carbon.system.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 报价信息VO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    private String type;

    /**
     * 项目列表
     */
    private List<QuotationProject> projects;
}





