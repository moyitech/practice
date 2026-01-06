package com.carbon.assets.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carbon.assets.constant.RocketDelayLevelConstant;
import com.carbon.assets.constant.RocketMqName;
import com.carbon.assets.entity.CarbonQuotaAssets;
import com.carbon.assets.mapper.CarbonQuotaAssetsMapper;
import com.carbon.assets.param.CarbonQuotaAssetsQueryParam;
import com.carbon.assets.service.BaseServiceImpl;
import com.carbon.assets.service.CarbonQuotaAssetsService;
import com.carbon.assets.vo.CarbonAssetsTotalVo;
import com.carbon.assets.vo.CarbonQuotaAssetsQueryVo;
import com.carbon.assets.vo.Paging;
import com.carbon.assets.vo.QuotaApproval;
import com.carbon.assets.vo.SecurityData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 碳配额资产服务实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CarbonQuotaAssetsServiceImpl extends BaseServiceImpl<CarbonQuotaAssetsMapper, CarbonQuotaAssets> implements CarbonQuotaAssetsService {

    @Resource
    private CarbonQuotaAssetsMapper carbonQuotaAssetsMapper;

    @Autowired(required = false)
    private RocketMQTemplate mqTemplate;

    @Override
    public CarbonQuotaAssetsQueryVo getCarbonQuotaAssetsById(Serializable id) {
        CarbonQuotaAssetsQueryVo vo = carbonQuotaAssetsMapper.getCarbonQuotaAssetsById(id);
        return vo;
    }

    @Override
    public Paging<CarbonQuotaAssetsQueryVo> getCarbonQuotaAssetsPageList(CarbonQuotaAssetsQueryParam param) {
        if (param == null) {
            param = new CarbonQuotaAssetsQueryParam();
        }
        Page<?> page = new Page<>(param.getCurrent(), param.getSize());
        page.addOrder(OrderItem.desc("cqa.updated_time"));
        IPage<CarbonQuotaAssetsQueryVo> iPage = carbonQuotaAssetsMapper.getCarbonQuotaAssetsPageList(page, param);
        return new Paging<>(iPage);
    }

    @Override
    public CarbonAssetsTotalVo getCarbonAssetsTotal() {
        return carbonQuotaAssetsMapper.getCarbonAssetsTotal();
    }

    @Override
    public void SendFeishuApprove(CarbonQuotaAssets carbonQuotaAssets) {
        if (mqTemplate == null) {
            log.warn("RocketMQ未配置，跳过飞书审批消息发送");
            return;
        }
        
        SecurityData data = getLoginInfoVo().getSecurityData();
        // 飞书审批
        QuotaApproval approval = QuotaApproval.builder()
                .id(carbonQuotaAssets.getId())
                .userName(handleNull(data.getAccountName()))
                .agenciesName(handleNull(data.getTenantName()))
                .contactNumber(handleNull(data.getPhone()))
                .assetType(handleNull("碳配额"))
                .primaryMarketHoldingInstitutions(handleNull(null))
                .shareholding(handleNull(carbonQuotaAssets.getTotal() != null ? carbonQuotaAssets.getTotal().toString() : null))
                .proofOfPosition(handleNull(carbonQuotaAssets.getIssuingCertificates()))
                .issuingAgency(handleNull(carbonQuotaAssets.getIssuingAgency()))
                .build();
        
        try {
            Message<QuotaApproval> msg = MessageBuilder.withPayload(approval).build();
            mqTemplate.syncSend(RocketMqName.QuotaApproval_MSG, msg, 3000, RocketDelayLevelConstant.SECOND5);
            log.info("飞书审批消息发送成功");
        } catch (Exception e) {
            log.error("飞书审批消息发送失败", e);
        }
    }

    private String handleNull(String str) {
        return str == null ? "-" : str;
    }
}

