package com.carbon.assets.service.feign;

import com.carbon.assets.param.CarbonCreditAssetsParam;
import com.carbon.assets.param.CarbonQuotaAssetsParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 区块链服务Feign客户端
 */
@FeignClient(name = "chain-maker", path = "/chain")
public interface ChainMakerServiceApi {

    /**
     * 添加碳信用资产到区块链
     * @param param 碳信用资产参数
     */
    @PostMapping("/creditAssets/add")
    void addCreditAssets(@RequestBody CarbonCreditAssetsParam param);

    /**
     * 添加碳配额资产到区块链
     * @param param 碳配额资产参数
     */
    @PostMapping("/quotaAssets/add")
    void addQuotaAssets(@RequestBody CarbonQuotaAssetsParam param);

}

