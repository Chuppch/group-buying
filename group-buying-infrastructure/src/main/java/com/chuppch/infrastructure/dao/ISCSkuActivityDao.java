package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.SCSkuActivity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chuppch
 * @desc 渠道商品活动配置标关联表Dao
 * @create 2025-4-18
 */
@Mapper
public interface ISCSkuActivityDao {

    SCSkuActivity querySCSkuActivityBySCGoodsId(SCSkuActivity scSkuActivity);

}
