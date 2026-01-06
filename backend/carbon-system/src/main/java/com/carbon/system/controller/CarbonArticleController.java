package com.carbon.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.carbon.system.common.ApiResult;
import com.carbon.system.common.BaseController;
import com.carbon.system.entity.CarbonArticle;
import com.carbon.system.service.CarbonArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 碳文章控制器
 */
@Slf4j
@RestController
@RequestMapping("/system/carbonArticle")
public class CarbonArticleController extends BaseController {

    @Autowired
    private CarbonArticleService carbonArticleService;

    /**
     * 分页查询文章列表
     * @param categoryId 分类ID（1-动态，2-知识库，3-公告，4-常见问题）
     * @param current 当前页，默认1
     * @param size 每页大小，默认10
     * @param asc 是否升序，默认false（降序）
     * @return 分页结果
     */
    @GetMapping("/getPageList")
    public ApiResult<IPage<CarbonArticle>> getPageList(
            @RequestParam(required = false) String categoryId,
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size,
            @RequestParam(required = false, defaultValue = "false") Boolean asc) {
        log.info("查询文章列表，categoryId: {}, current: {}, size: {}, asc: {}", categoryId, current, size, asc);
        
        Page<CarbonArticle> page = new Page<>(current, size);
        IPage<CarbonArticle> result = carbonArticleService.getPageList(page, categoryId, asc);
        
        return ApiResult.ok(result);
    }
}

