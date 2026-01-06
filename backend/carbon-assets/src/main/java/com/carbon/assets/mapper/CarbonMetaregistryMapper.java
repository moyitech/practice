package com.carbon.assets.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbon.assets.entity.CarbonMetaregistry;
import com.carbon.assets.vo.CarbonMetaregistryQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * 碳元注册Mapper接口
 */
@Repository
public interface CarbonMetaregistryMapper extends BaseMapper<CarbonMetaregistry> {

    /**
     * 根据ID获取查询对象
     * @param id 主键id
     * @return CarbonMetaregistryQueryVo
     */
    CarbonMetaregistryQueryVo getCarbonMetaregistryById(@Param("id") Serializable id);

}

