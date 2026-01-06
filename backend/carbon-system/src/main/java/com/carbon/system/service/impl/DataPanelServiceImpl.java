package com.carbon.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.carbon.system.entity.DataPanel;
import com.carbon.system.entity.SysAccount;
import com.carbon.system.entity.SysTenant;
import com.carbon.system.mapper.DataPanelMapper;
import com.carbon.system.service.*;
import com.carbon.system.vo.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据面板服务实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class DataPanelServiceImpl extends BaseServiceImpl<DataPanelMapper, DataPanel> implements DataPanelService {

    @Resource
    private DataPanelMapper dataPanelMapper;

    @Autowired
    private SysAccountService sysAccountService;

    @Autowired
    private SysTenantService sysTenantService;

    @Autowired
    private SysAccountRoleService sysAccountRoleService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public StatHomeDataVo getHomeData() {
        StatHomeDataVo vo;

        // 根据租户ID查询数据面板
        DataPanel dataPanel = this.getOne(Wrappers.<DataPanel>lambdaQuery()
                .eq(DataPanel::getTenantId, getCurrentTenantId()));

        if (dataPanel == null) {
            vo = new StatHomeDataVo();
        } else {
            vo = dataPanel.getHomeData();
        }

        // 查询账户信息
        SysAccount account = sysAccountService.getById(getCurrentAccountId());
        SysTenant tenant = sysTenantService.getById(getCurrentTenantId());
        SysAccountRoleVo accountRole = sysRoleService.getAccountRole(account.getId());

        // 构建账户统计信息
        StatAccountVo accountVo = new StatAccountVo();
        accountVo.setAccountName(account.getAccountName());
        accountVo.setAccountValidity(account.getValidityPeriod());
        accountVo.setExpired(DateUtil.compare(DateUtil.date(), accountVo.getAccountValidity()) > 0);
        accountVo.setAvatar(account.getAvatar());
        accountVo.setAccountType(account.getAccountType());
        accountVo.setProductVersion(account.getProductVersion());
        accountVo.setEnterpriseName(tenant == null ? "null" : tenant.getTenantName());
        accountVo.setRoleNames(accountRole != null ? accountRole.getRoleNames() : null);

        // 上个月
        Date lastMonth = DateUtil.offsetMonth(DateUtil.date(), -1);
        Date statDate = DateUtil.date();

        // 查询碳信用和碳配额总计
        BigDecimal carbonCreditTotal = dataPanelMapper.getCarbonCreditTotal(null);
        BigDecimal carbonQuotaTotal = dataPanelMapper.getCarbonQuotaTotal(null);
        BigDecimal carbonCreditMonth = dataPanelMapper.getCarbonCreditTotal(lastMonth);
        BigDecimal carbonQuotaMonth = dataPanelMapper.getCarbonQuotaTotal(lastMonth);

        // 资产收入
        StatCarbonIncomeVo assetsIncome = new StatCarbonIncomeVo();
        assetsIncome.setStatDate(statDate);
        assetsIncome.setMonthIncome(carbonCreditMonth.add(carbonQuotaMonth));
        assetsIncome.setTotalIncome(carbonCreditTotal.add(carbonQuotaTotal));
        assetsIncome.setMonthOnMonthRatio(BigDecimal.ZERO);
        assetsIncome.setYearOnYearRatio(BigDecimal.ZERO);
        vo.setAssetsIncome(assetsIncome);

        // 资金收入
        StatCarbonIncomeVo fundIncome = new StatCarbonIncomeVo();
        fundIncome.setStatDate(statDate);
        fundIncome.setMonthIncome(BigDecimal.ZERO);
        fundIncome.setTotalIncome(BigDecimal.ZERO);
        fundIncome.setMonthOnMonthRatio(BigDecimal.ZERO);
        fundIncome.setYearOnYearRatio(BigDecimal.ZERO);
        vo.setFundIncome(fundIncome);

        vo.setCarbonCredit(carbonCreditTotal);
        vo.setCarbonQuota(carbonQuotaTotal);
        vo.setGreenScore(BigDecimal.ZERO);

        // 供应量
        StatCarbonMonthVo monthSupply = new StatCarbonMonthVo();
        monthSupply.setStatDate(statDate);
        monthSupply.setCarbonCredit(carbonCreditMonth);
        monthSupply.setCarbonQuota(carbonQuotaMonth);
        monthSupply.setGreenScore(BigDecimal.ZERO);
        monthSupply.setCarbonSupply(carbonCreditMonth.add(carbonQuotaMonth));
        monthSupply.setCarbonSupplyTotal(carbonCreditTotal.add(carbonQuotaTotal));
        monthSupply.setCarbonValuation(getAmount(monthSupply.getCarbonSupplyTotal()));
        vo.setMonthSupply(monthSupply);

        // 开发量
        vo.setMonthDevelopment(monthSupply);

        // 销售量
        StatCarbonMonthVo monthSales = new StatCarbonMonthVo();
        monthSales.setStatDate(statDate);
        monthSales.setCarbonCredit(BigDecimal.ZERO);
        monthSales.setCarbonQuota(BigDecimal.ZERO);
        monthSales.setGreenScore(BigDecimal.ZERO);
        monthSales.setCarbonSupply(BigDecimal.ZERO);
        monthSales.setCarbonSupplyTotal(BigDecimal.ZERO);
        monthSales.setCarbonValuation(BigDecimal.ZERO);
        vo.setMonthSales(monthSales);

        // 项目统计
        vo.setProjectStat(getCarbonProjectVo());

        vo.setAccountVo(accountVo);
        return vo;
    }

    /**
     * 获取碳项目统计信息
     */
    private StatCarbonProjectVo getCarbonProjectVo() {
        StatCarbonProjectVo vo = dataPanelMapper.getProjectCountStat();

        List<StatCarbonProject> projectList = dataPanelMapper.getProjectList();
        for (StatCarbonProject project : projectList) {
            long reduction = 0;
            try {
                reduction = Long.parseLong(StrUtil.trim(project.getReduction()));
            } catch (NumberFormatException e) {
                log.error("首页-数据面板，reduction 转换异常");
            }
            project.setCarbonValuation(getAmount(BigDecimal.valueOf(reduction)));
        }
        vo.setProjectList(projectList);
        return vo;
    }

    /**
     * 计算碳估值（简单按50元/吨计算）
     */
    private BigDecimal getAmount(BigDecimal carbonEmission) {
        return carbonEmission.multiply(BigDecimal.valueOf(50));
    }

    @Override
    public EnterpriseGreennessVo getGreenness() {
        EnterpriseGreennessVo greennessVo = new EnterpriseGreennessVo();
        greennessVo.setCarbonEmissionRate(30.0);
        greennessVo.setEnergyEfficiencyRate(20.0);
        greennessVo.setEnvironmentRate(50.0);
        return greennessVo;
    }
}


