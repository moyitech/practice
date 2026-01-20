package com.carbon.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbon.system.entity.DataPanel;
import com.carbon.system.vo.StatCarbonProject;
import com.carbon.system.vo.StatCarbonProjectVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据面板Mapper接口
 */
@Repository
public interface DataPanelMapper extends BaseMapper<DataPanel> {

    /**
     * 获取碳信用总计
     * @param statDate 统计日期（如果不为null，则只统计当月）
     * @return 碳信用总计
     */
    BigDecimal getCarbonCreditTotal(@Param("statDate") Date statDate);

    /**
     * 获取碳配额总计
     * @param statDate 统计日期（如果不为null，则只统计当月）
     * @return 碳配额总计
     */
    BigDecimal getCarbonQuotaTotal(@Param("statDate") Date statDate);

    /**
     * 获取项目计数统计信息
     * @return 项目统计信息
     */
    StatCarbonProjectVo getProjectCountStat();

    /**
     * 获取碳项目列表
     * @return 项目列表
     */
    List<StatCarbonProject> getProjectList();
}










