package com.carbon.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carbon.system.entity.CarbonArticle;
import com.carbon.system.mapper.CarbonArticleMapper;
import com.carbon.system.service.BaseServiceImpl;
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
        try {
            LambdaQueryWrapper<CarbonArticle> queryWrapper = new LambdaQueryWrapper<>();
            
            // 如果指定了分类ID，则按分类查询
            if (categoryId != null && !categoryId.isEmpty()) {
                queryWrapper.eq(CarbonArticle::getCategoryId, categoryId);
            }
            
            // 注意：如果数据库表中有 published 字段，可以取消下面的注释
            // queryWrapper.eq(CarbonArticle::getPublished, 1);
            
            // 排序：按创建时间排序
            if (asc != null && asc) {
                queryWrapper.orderByAsc(CarbonArticle::getCreateTime);
            } else {
                queryWrapper.orderByDesc(CarbonArticle::getCreateTime);
            }
            
            // 注意：如果数据库表中有 tenant_id 字段，可以取消下面的注释
            // 按租户ID过滤（如果获取不到租户ID，则不添加此条件，允许查询所有租户的数据）
            // try {
            //     Long tenantId = getCurrentTenantId();
            //     if (tenantId != null) {
            //         queryWrapper.eq(CarbonArticle::getTenantId, tenantId);
            //     }
            // } catch (Exception e) {
            //     log.warn("获取租户ID失败，将查询所有租户的数据: {}", e.getMessage());
            // }
            
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("查询文章列表失败", e);
            throw new RuntimeException("查询文章列表失败: " + e.getMessage(), e);
        }
    }
}

