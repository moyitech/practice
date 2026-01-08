package com.carbon.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 企业绿度VO
 */
@Data
public class EnterpriseGreennessVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 碳排放率
     */
    private Double carbonEmissionRate;

    /**
     * 能源效率率
     */
    private Double energyEfficiencyRate;

    /**
     * 环境率
     */
    private Double environmentRate;
}





