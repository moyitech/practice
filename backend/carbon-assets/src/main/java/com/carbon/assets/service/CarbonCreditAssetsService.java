package com.carbon.assets.service;

import com.carbon.assets.entity.CarbonCreditAssets;
import com.carbon.assets.param.CarbonCreditAssetsAddParam;
import com.carbon.assets.param.CarbonCreditAssetsQueryParam;
import com.carbon.assets.vo.CarbonAssetsTotalVo;
import com.carbon.assets.vo.CarbonCreditAssetsQueryVo;
import com.carbon.assets.vo.Paging;

import java.io.Serializable;

/**
 * 碳信用资产服务接口
 */
public interface CarbonCreditAssetsService extends BaseService<CarbonCreditAssets> {

    /**
     * 根据ID获取查询对象
     * @param id 主键id
     * @return CarbonCreditAssetsQueryVo
     */
    CarbonCreditAssetsQueryVo getCarbonCreditAssetsById(Serializable id);

    /**
     * 获取分页对象
     * @param param CarbonCreditAssetsQueryParam
     * @return Paging<CarbonCreditAssetsQueryVo>
     */
    Paging<CarbonCreditAssetsQueryVo> getCarbonCreditAssetsPageList(CarbonCreditAssetsQueryParam param);

    /**
     * 添加碳信用资产
     * @param param 资产参数
     */
    void addCarbonCreditAssets(CarbonCreditAssetsAddParam param);

    /**
     * 获取碳资产总额
     * @return CarbonAssetsTotalVo
     */
    CarbonAssetsTotalVo getCarbonAssetsTotal();

    @Override
    boolean removeById(Serializable id);

}

