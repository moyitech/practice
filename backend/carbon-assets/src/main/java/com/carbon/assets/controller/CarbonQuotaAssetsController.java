package com.carbon.assets.controller;

import cn.hutool.core.bean.BeanUtil;
import com.carbon.assets.common.ApiResult;
import com.carbon.assets.common.BaseController;
import com.carbon.assets.entity.CarbonQuotaAssets;
import com.carbon.assets.param.CarbonQuotaAssetsParam;
import com.carbon.assets.param.CarbonQuotaAssetsQueryParam;
import com.carbon.assets.service.CarbonQuotaAssetsService;
import com.carbon.assets.service.feign.ChainMakerServiceApi;
import com.carbon.assets.vo.CarbonAssetsTotalVo;
import com.carbon.assets.vo.CarbonQuotaAssetsQueryVo;
import com.carbon.assets.vo.Paging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 碳配额资产控制器
 */
@Slf4j
@RestController
@RequestMapping("/assets/carbonQuotaAssets")
@Tag(name = "碳配额资产模块", description = "碳配额资产模块")
public class CarbonQuotaAssetsController extends BaseController {

    @Autowired
    private CarbonQuotaAssetsService carbonQuotaAssetsService;

    @Autowired(required = false)
    private ChainMakerServiceApi chainMakerServiceApi;

    /**
     * 添加碳配额资产
     */
    @PostMapping("/add")
    @Operation(summary = "添加碳配额资产", description = "添加碳配额资产")
    public ApiResult<Boolean> addCarbonQuotaAssets(@Valid @RequestBody CarbonQuotaAssets carbonQuotaAssets) {
        carbonQuotaAssets.setAvailableAmount(carbonQuotaAssets.getTotal());
        // 资产估值=总值*50
        carbonQuotaAssets.setValuation(carbonQuotaAssets.getTotal().multiply(new BigDecimal("50.00")));
        carbonQuotaAssets.setTenantId(getCurrentTenantId());
        boolean flag = carbonQuotaAssetsService.save(carbonQuotaAssets);
        
        // 发送mq飞书审批消息
        carbonQuotaAssetsService.SendFeishuApprove(carbonQuotaAssets);
        
        // 长安链-上链
        if (chainMakerServiceApi != null) {
            try {
                CarbonQuotaAssetsParam param = new CarbonQuotaAssetsParam();
                BeanUtil.copyProperties(carbonQuotaAssets, param);
                chainMakerServiceApi.addQuotaAssets(param);
                log.info("区块链消息发送成功");
            } catch (Exception e) {
                log.error("区块链消息异常", e);
            }
        }
        
        return ApiResult.result(flag);
    }

    /**
     * 修改碳配额资产
     */
    @PutMapping("/update")
    @Operation(summary = "修改碳配额资产", description = "修改碳配额资产")
    public ApiResult<Boolean> updateCarbonQuotaAssets(@Valid @RequestBody CarbonQuotaAssets carbonQuotaAssets) {
        boolean flag = carbonQuotaAssetsService.updateById(carbonQuotaAssets);
        return ApiResult.result(flag);
    }

    /**
     * 获取碳配额资产
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "碳配额资产-详情", description = "查看碳配额资产")
    public ApiResult<CarbonQuotaAssetsQueryVo> getCarbonQuotaAssets(@PathVariable String id) {
        CarbonQuotaAssetsQueryVo carbonQuotaAssetsQueryVo = carbonQuotaAssetsService.getCarbonQuotaAssetsById(id);
        return ApiResult.ok(carbonQuotaAssetsQueryVo);
    }

    /**
     * 碳配额资产分页列表
     */
    @PostMapping("/getPageList")
    @Operation(summary = "碳配额资产-分页列表", description = "碳配额资产分页列表")
    public ApiResult<Paging<CarbonQuotaAssetsQueryVo>> getCarbonQuotaAssetsPageList(@Valid @RequestBody(required = false) CarbonQuotaAssetsQueryParam carbonQuotaAssetsQueryParam) {
        Paging<CarbonQuotaAssetsQueryVo> paging = carbonQuotaAssetsService.getCarbonQuotaAssetsPageList(carbonQuotaAssetsQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 获取碳配额资产总额
     */
    @GetMapping("/assetsTotal")
    @Operation(summary = "碳配额资产-总计", description = "碳配额资产-总计")
    public ApiResult<CarbonAssetsTotalVo> getCarbonAssetsTotal() {
        CarbonAssetsTotalVo vo = carbonQuotaAssetsService.getCarbonAssetsTotal();
        return ApiResult.ok(vo);
    }

}

