package com.carbon.assets.controller;

import com.carbon.assets.common.ApiResult;
import com.carbon.assets.common.BaseController;
import com.carbon.assets.entity.CarbonCreditAssets;
import com.carbon.assets.param.CarbonCreditAssetsAddParam;
import com.carbon.assets.param.CarbonCreditAssetsQueryParam;
import com.carbon.assets.service.CarbonCreditAssetsService;
import com.carbon.assets.vo.CarbonAssetsTotalVo;
import com.carbon.assets.vo.CarbonCreditAssetsQueryVo;
import com.carbon.assets.vo.Paging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 碳信用资产控制器
 */
@Slf4j
@RestController
@RequestMapping("/assets/carbonCreditAssets")
@Tag(name = "碳信用资产模块", description = "碳信用资产模块")
public class CarbonCreditAssetsController extends BaseController {

    @Autowired
    private CarbonCreditAssetsService carbonCreditAssetsService;

    /**
     * 添加碳信用资产
     */
    @PostMapping("/add")
    @Operation(summary = "添加碳信用资产", description = "添加碳信用资产")
    public ApiResult<Boolean> addCarbonCreditAssets(@Valid @RequestBody CarbonCreditAssetsAddParam param) {
        carbonCreditAssetsService.addCarbonCreditAssets(param);
        return ApiResult.ok();
    }

    /**
     * 修改碳信用资产
     */
    @PutMapping("/update")
    @Operation(summary = "修改碳信用资产", description = "修改碳信用资产")
    public ApiResult<Boolean> updateCarbonCreditAssets(@Valid @RequestBody CarbonCreditAssets carbonCreditAssets) {
        boolean flag = carbonCreditAssetsService.updateById(carbonCreditAssets);
        return ApiResult.result(flag);
    }

    /**
     * 获取碳信用资产
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "碳信用资产-详情", description = "查看碳信用资产")
    public ApiResult<CarbonCreditAssetsQueryVo> getCarbonCreditAssets(@PathVariable String id) {
        CarbonCreditAssetsQueryVo carbonCreditAssetsQueryVo = carbonCreditAssetsService.getCarbonCreditAssetsById(id);
        return ApiResult.ok(carbonCreditAssetsQueryVo);
    }

    /**
     * 碳信用资产分页列表
     */
    @PostMapping("/getPageList")
    @Operation(summary = "碳信用资产-分页列表", description = "碳信用资产分页列表")
    public ApiResult<Paging<CarbonCreditAssetsQueryVo>> getCarbonCreditAssetsPageList(@Valid @RequestBody(required = false) CarbonCreditAssetsQueryParam carbonCreditAssetsQueryParam) {
        Paging<CarbonCreditAssetsQueryVo> paging = carbonCreditAssetsService.getCarbonCreditAssetsPageList(carbonCreditAssetsQueryParam);
        return ApiResult.ok(paging);
    }

    /**
     * 获取碳资产总额
     */
    @GetMapping("/assetsTotal")
    @Operation(summary = "碳配额资产-总计", description = "碳配额资产-总计")
    public ApiResult<CarbonAssetsTotalVo> getCarbonAssetsTotal() {
        CarbonAssetsTotalVo vo = carbonCreditAssetsService.getCarbonAssetsTotal();
        return ApiResult.ok(vo);
    }

    /**
     * 删除碳信用对象
     */
    @PostMapping("/delete")
    @Operation(summary = "删除碳信用对象", description = "删除")
    public ApiResult<Boolean> deleteCarbonCreditAsset(@RequestParam String id) {
        boolean flag = carbonCreditAssetsService.removeById(id);
        return ApiResult.result(flag);
    }

}

