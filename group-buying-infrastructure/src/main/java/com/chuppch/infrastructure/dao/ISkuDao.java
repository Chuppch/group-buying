package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.Sku;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chuppch
 * @description 商品查询
 * @create 2025-416
 */
@Mapper
public interface ISkuDao {

    Sku querySkuGoodsId(String goodsId);

}
