package com.carbon.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carbon.system.entity.CarbonArticle;
import com.carbon.system.mapper.CarbonArticleMapper;
import com.carbon.system.service.BaseService;
import com.carbon.system.service.CarbonArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 碳文章服务实现类
 */
@Slf4j
@Service
public class CarbonArticleServiceImpl extends BaseServiceImpl<CarbonArticleMapper, CarbonArticle> implements CarbonArticleService {

    @Override
    public IPage<CarbonArticle> getPageList(Page<CarbonArticle> page, String categoryId, Boolean asc) {
        LambdaQueryWrapper<CarbonArticle> queryWrapper = new LambdaQueryWrapper<>();
        
        // 如果指定了分类ID，则按分类查询
        if (categoryId != null && !categoryId.isEmpty()) {
            queryWrapper.eq(CarbonArticle::getCategoryId, categoryId);
        }
        
        // 只查询已发布的文章
        queryWrapper.eq(CarbonArticle::getPublished, 1);
        
        // 排序：按创建时间排序
        if (asc != null && asc) {
            queryWrapper.orderByAsc(CarbonArticle::getCreateTime);
        } else {
            queryWrapper.orderByDesc(CarbonArticle::getCreateTime);
        }
        
        // 按租户ID过滤
        queryWrapper.eq(CarbonArticle::getTenantId, getCurrentTenantId());
        
        return this.page(page, queryWrapper);
    }
}

