package com.carbon.system.service;

import com.carbon.system.entity.DataPanel;
import com.carbon.system.vo.EnterpriseGreennessVo;
import com.carbon.system.vo.StatHomeDataVo;

/**
 * 数据面板服务接口
 */
public interface DataPanelService extends BaseService<DataPanel> {

    /**
     * 获取首页数据
     * @return 首页统计数据
     */
    StatHomeDataVo getHomeData();

    /**
     * 获取企业绿度
     * @return 企业绿度VO
     */
    EnterpriseGreennessVo getGreenness();
}









