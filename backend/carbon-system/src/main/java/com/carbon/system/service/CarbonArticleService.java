package com.carbon.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carbon.system.entity.CarbonArticle;

/**
 * 碳文章服务接口
 */
public interface CarbonArticleService extends BaseService<CarbonArticle> {

    /**
     * 分页查询文章列表
     * @param page 分页对象
     * @param categoryId 分类ID
     * @param asc 是否升序
     * @return 分页结果
     */
    IPage<CarbonArticle> getPageList(Page<CarbonArticle> page, String categoryId, Boolean asc);
}

