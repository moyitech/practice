package com.carbon.assets.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carbon.assets.entity.CarbonCreditAssets;
import com.carbon.assets.param.CarbonCreditAssetsQueryParam;
import com.carbon.assets.vo.CarbonAssetsTotalVo;
import com.carbon.assets.vo.CarbonCreditAssetsQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 碳信用资产Mapper接口
 */
@Repository
public interface CarbonCreditAssetsMapper extends BaseMapper<CarbonCreditAssets> {

    /**
     * 根据ID获取查询对象
     * @param id 主键id
     * @return CarbonCreditAssetsQueryVo
     */
    CarbonCreditAssetsQueryVo getCarbonCreditAssetsById(Serializable id);

    /**
     * 获取分页对象
     * @param page 分页参数
     * @param param 查询参数
     * @return IPage<CarbonCreditAssetsQueryVo>
     */
    IPage<CarbonCreditAssetsQueryVo> getCarbonCreditAssetsPageList(@Param("page") Page<?> page, @Param("param") CarbonCreditAssetsQueryParam param);

    /**
     * 获取碳资产总额
     * @return CarbonAssetsTotalVo
     */
    CarbonAssetsTotalVo getCarbonAssetsTotal();

}

