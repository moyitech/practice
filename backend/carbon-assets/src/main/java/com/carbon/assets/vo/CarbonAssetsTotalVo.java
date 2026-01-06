package com.carbon.assets.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 碳资产总额VO
 */
@Data
public class CarbonAssetsTotalVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总量
     */
    private BigDecimal total;

    /**
     * 可用数量
     */
    private BigDecimal availableAmount;

    /**
     * 冻结数量
     */
    private BigDecimal frozenAmount;

    /**
     * 锁定数量
     */
    private BigDecimal lockedAmount;

}

