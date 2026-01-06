package com.carbon.assets.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carbon.assets.constant.AssetsStatus;
import com.carbon.assets.constant.AssetsTradeStatus;
import com.carbon.assets.constant.RocketDelayLevelConstant;
import com.carbon.assets.constant.RocketMqName;
import com.carbon.assets.entity.CarbonCreditAssets;
import com.carbon.assets.entity.CarbonMetaregistry;
import com.carbon.assets.entity.CarbonMetaregistryProject;
import com.carbon.assets.mapper.CarbonCreditAssetsMapper;
import com.carbon.assets.mapper.CarbonMetaregistryMapper;
import com.carbon.assets.mapper.CarbonMetaregistryProjectMapper;
import com.carbon.assets.param.CarbonCreditAssetsAddParam;
import com.carbon.assets.param.CarbonCreditAssetsParam;
import com.carbon.assets.param.CarbonCreditAssetsQueryParam;
import com.carbon.assets.service.BaseServiceImpl;
import com.carbon.assets.service.CarbonCreditAssetsService;
import com.carbon.assets.service.feign.ChainMakerServiceApi;
import com.carbon.assets.service.feign.SystemServiceApi;
import com.carbon.assets.vo.AssetUploadApproval;
import com.carbon.assets.vo.CarbonAssetsTotalVo;
import com.carbon.assets.vo.CarbonCreditAssetsQueryVo;
import com.carbon.assets.vo.CarbonMetaregistryQueryVo;
import com.carbon.assets.vo.Paging;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 碳信用资产服务实现类
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class CarbonCreditAssetsServiceImpl extends BaseServiceImpl<CarbonCreditAssetsMapper, CarbonCreditAssets> implements CarbonCreditAssetsService {

    @Resource
    private CarbonCreditAssetsMapper carbonCreditAssetsMapper;
    @Resource
    private CarbonMetaregistryProjectMapper carbonMetaregistryProjectMapper;
    @Resource
    private CarbonMetaregistryMapper carbonMetaregistryMapper;

    @Autowired(required = false)
    private RocketMQTemplate mqTemplate;

    @Autowired(required = false)
    private SystemServiceApi systemServiceApi;

    @Autowired(required = false)
    private ChainMakerServiceApi chainMakerServiceApi;

    @Override
    public CarbonCreditAssetsQueryVo getCarbonCreditAssetsById(Serializable id) {
        CarbonCreditAssetsQueryVo vo = carbonCreditAssetsMapper.getCarbonCreditAssetsById(id);
        if (vo == null) {
            return null;
        }
        
        // vo.getCarbonProjectId()是carbon_metaregistry_project表的id
        // 需要通过carbon_metaregistry_project表获取metaregistry_id，再查询carbon_metaregistry表
        CarbonMetaregistryProject project = carbonMetaregistryProjectMapper.selectById(vo.getCarbonProjectId());
        if (project == null) {
            return vo;
        }
        
        CarbonMetaregistry carbonMetaregistry = carbonMetaregistryMapper.selectById(project.getMetaregistryId());

        if (carbonMetaregistry != null) {
            String certifiedStandard = carbonMetaregistry.getCertifiedStandard();
            if (StrUtil.isNotBlank(certifiedStandard)) {
                vo.setCertificationCriteria(certifiedStandard);
                if ("CCER".equals(certifiedStandard)) {
                    vo.setIssuingAgency("生态环境部");
                } else if ("VCS".equals(certifiedStandard)) {
                    vo.setIssuingAgency("verra");
                } else if ("goldstandard".equals(certifiedStandard)) {
                    vo.setIssuingAgency(certifiedStandard);
                }
            }

            vo.setCarbonMethodologyName(carbonMetaregistry.getMethodologyCode());
            vo.setProjectScopeTypeCode(carbonMetaregistry.getProjectScopeTypeCode());
            vo.setCertifiedAgency(carbonMetaregistry.getProjectVerifier());
            vo.setIssuingDate(carbonMetaregistry.getIssuingDate());
        }
        return vo;
    }

    @Override
    public Paging<CarbonCreditAssetsQueryVo> getCarbonCreditAssetsPageList(CarbonCreditAssetsQueryParam param) {
        if (param == null) {
            param = new CarbonCreditAssetsQueryParam();
        }
        Page<?> page = new Page<>(param.getCurrent(), param.getSize());
        page.addOrder(OrderItem.desc("cca.updated_time"));
        IPage<CarbonCreditAssetsQueryVo> iPage = carbonCreditAssetsMapper.getCarbonCreditAssetsPageList(page, param);
        List<CarbonCreditAssetsQueryVo> records = iPage.getRecords();
        return new Paging<>(iPage, records);
    }

    @Override
    public void addCarbonCreditAssets(CarbonCreditAssetsAddParam param) {

        CarbonCreditAssets carbonCreditAssets = new CarbonCreditAssets();
        BeanUtil.copyProperties(param, carbonCreditAssets, "id", "carbonProjectId");
        
        // 查询项目库（param.getCarbonProjectId()是carbon_metaregistry表的id）
        CarbonMetaregistryQueryVo cm = carbonMetaregistryMapper.getCarbonMetaregistryById(param.getCarbonProjectId());
        if (cm == null) {
            throw new RuntimeException("项目库不存在");
        }
        log.info("this carbonMetaregistry.object : {}", JSONUtil.toJsonStr(cm));
        
        // 插入一个 CarbonMetaRegistryProject
        CarbonMetaregistryProject project = new CarbonMetaregistryProject();
        project.setMetaregistryId(cm.getId());
        project.setTenantId(getCurrentTenantId());
        carbonMetaregistryProjectMapper.insert(project);
        log.info("projectId : {}", project.getId());
        
        // 保存（将新插入的carbon_metaregistry_project的id设置到carbon_project_id）
        carbonCreditAssets.setCarbonProjectId(project.getId());
        carbonCreditAssets.setTenantId(getCurrentTenantId());
        carbonCreditAssets.setAssetsStatus(AssetsStatus.WAIT.getStatus());
        carbonCreditAssets.setTransactionStatus(AssetsTradeStatus.OFFER.getStatus());
        carbonCreditAssets.setAvailableAmount(carbonCreditAssets.getTotal());
        carbonCreditAssets.setValuation(carbonCreditAssets.getTotal().multiply(BigDecimal.valueOf(50)));
        carbonCreditAssets.setCertifiedAgency(cm.getCertifiedStandard());
        this.save(carbonCreditAssets);

        SecurityData data = getLoginInfoVo().getSecurityData();
        
        // 飞书审批
        AssetUploadApproval approval = AssetUploadApproval.builder()
                .id(carbonCreditAssets.getId())
                .userName(handleNull(data.getAccountName()))
                .agenciesName(handleNull(data.getTenantName()))
                .contactNumber(handleNull(data.getPhone()))
                .assetType(handleNull("碳信用"))
                .primaryMarketHoldingInstitutions(handleNull(null))
                .shareholding(handleNull(carbonCreditAssets.getTotal().toString()))
                .proofOfPosition(handleNull(carbonCreditAssets.getIssuingCertificates()))
                .issuingAgency(handleNull(carbonCreditAssets.getIssuingAgency()))
                .build();
        
        if (mqTemplate != null) {
            try {
                Message<AssetUploadApproval> msg = MessageBuilder.withPayload(approval).build();
                mqTemplate.syncSend(RocketMqName.AssetUploadApproval_MSG, msg, 3000, RocketDelayLevelConstant.SECOND5);
                log.info("飞书审批消息发送成功");
            } catch (Exception e) {
                log.error("飞书审批消息发送失败", e);
            }
        }

        // 异步发送消息到区块链
        if (chainMakerServiceApi != null) {
            try {
                CarbonCreditAssetsParam chainParam = new CarbonCreditAssetsParam();
                BeanUtil.copyProperties(carbonCreditAssets, chainParam);
                chainMakerServiceApi.addCreditAssets(chainParam);
                log.info("区块链消息发送成功");
            } catch (Exception e) {
                log.error("区块链消息异常！！", e);
            }
        }
    }

    private String handleNull(String str) {
        return str == null ? "-" : str;
    }

    @Override
    public CarbonAssetsTotalVo getCarbonAssetsTotal() {
        return carbonCreditAssetsMapper.getCarbonAssetsTotal();
    }

    @Override
    public boolean removeById(Serializable id) {
        CarbonCreditAssetsQueryVo vo = this.getBaseMapper().getCarbonCreditAssetsById(id);
        if (vo == null) {
            return false;
        }
        Long projectId = vo.getCarbonProjectId();
        int deleteCount = this.getBaseMapper().deleteById(id);
        boolean res = deleteCount > 0;
        if (res) {
            Optional.ofNullable(projectId).ifPresent(e -> {
                carbonMetaregistryProjectMapper.deleteById(e);
            });
        }
        return res;
    }
}

