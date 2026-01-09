package com.carbon.system.controller;

import com.carbon.system.common.ApiResult;
import com.carbon.system.common.BaseController;
import com.carbon.system.service.DataPanelService;
import com.carbon.system.vo.EnterpriseGreennessVo;
import com.carbon.system.vo.StatHomeDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据面板控制器
 */
@Slf4j
@RestController
@RequestMapping("/dataPanel")
public class DataPanelController extends BaseController {

    @Autowired
    private DataPanelService dataPanelService;

    /**
     * 获取首页数据
     * @return 首页统计数据
     */
    @GetMapping("/home")
    public ApiResult<StatHomeDataVo> getHomeData() {
        log.info("获取首页数据");
        return ApiResult.ok(dataPanelService.getHomeData());
    }

    /**
     * 获取企业绿度
     * @return 企业绿度VO
     */
    @GetMapping("/getGreenness")
    public ApiResult<EnterpriseGreennessVo> getGreenness() {
        log.info("获取企业绿度");
        return ApiResult.ok(dataPanelService.getGreenness());
    }
}









