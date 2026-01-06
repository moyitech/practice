package com.carbon.system.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 碳项目统计VO
 */
@Data
public class StatCarbonProjectVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 减排总量
     */
    private String reductionTotal;

    /**
     * 签约数量
     */
    private Integer singCount;

    /**
     * 备案数量
     */
    private Integer filingCount;

    /**
     * 核准数量
     */
    private Integer approvedCount;

    /**
     * 项目列表
     */
    private List<StatCarbonProject> projectList;
}


