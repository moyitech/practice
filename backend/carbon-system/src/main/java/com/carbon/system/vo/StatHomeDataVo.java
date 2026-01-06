package com.carbon.system.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 首页统计数据VO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatHomeDataVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账户信息
     */
    private StatAccountVo accountVo;

    /**
     * 碳信用总计
     */
    private BigDecimal carbonCredit = BigDecimal.ZERO;

    /**
     * 碳配额总计
     */
    private BigDecimal carbonQuota = BigDecimal.ZERO;

    /**
     * 绿色积分
     */
    private BigDecimal greenScore = BigDecimal.ZERO;

    /**
     * 资产收入
     */
    private StatCarbonIncomeVo assetsIncome;

    /**
     * 资金收入
     */
    private StatCarbonIncomeVo fundIncome;

    /**
     * 月度供应量
     */
    private StatCarbonMonthVo monthSupply;

    /**
     * 月度开发量
     */
    private StatCarbonMonthVo monthDevelopment;

    /**
     * 月度销售量
     */
    private StatCarbonMonthVo monthSales;

    /**
     * 项目统计
     */
    private StatCarbonProjectVo projectStat;

    /**
     * 报价信息列表
     */
    private List<QuotationVo> quotation;
}

