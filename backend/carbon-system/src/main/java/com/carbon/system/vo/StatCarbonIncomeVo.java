package com.carbon.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 碳收入统计VO
 */
@Data
public class StatCarbonIncomeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 统计日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date statDate;

    /**
     * 月度收入
     */
    private BigDecimal monthIncome = BigDecimal.ZERO;

    /**
     * 总收入
     */
    private BigDecimal totalIncome = BigDecimal.ZERO;

    /**
     * 环比增长率
     */
    private BigDecimal monthOnMonthRatio = BigDecimal.ZERO;

    /**
     * 同比增长率
     */
    private BigDecimal yearOnYearRatio = BigDecimal.ZERO;
}









