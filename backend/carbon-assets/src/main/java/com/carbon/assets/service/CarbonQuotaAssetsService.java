package com.carbon.assets.service;

import com.carbon.assets.entity.CarbonQuotaAssets;
import com.carbon.assets.param.CarbonQuotaAssetsQueryParam;
import com.carbon.assets.vo.CarbonAssetsTotalVo;
import com.carbon.assets.vo.CarbonQuotaAssetsQueryVo;
import com.carbon.assets.vo.Paging;

import java.io.Serializable;

/**
 * 碳配额资产服务接口
 */
public interface CarbonQuotaAssetsService extends BaseService<CarbonQuotaAssets> {

    /**
     * 根据ID获取查询对象
     * @param id 主键id
     * @return CarbonQuotaAssetsQueryVo
     */
    CarbonQuotaAssetsQueryVo getCarbonQuotaAssetsById(Serializable id);

    /**
     * 获取分页对象
     * @param param CarbonQuotaAssetsQueryParam
     * @return Paging<CarbonQuotaAssetsQueryVo>
     */
    Paging<CarbonQuotaAssetsQueryVo> getCarbonQuotaAssetsPageList(CarbonQuotaAssetsQueryParam param);

    /**
     * 获取碳配额资产总额
     * @return CarbonAssetsTotalVo
     */
    CarbonAssetsTotalVo getCarbonAssetsTotal();

    /**
     * 发送飞书审批消息
     * @param carbonQuotaAssets 碳配额资产
     */
    void SendFeishuApprove(CarbonQuotaAssets carbonQuotaAssets);

}

