package com.chuppch.api.dto;

import lombok.Data;

/**
 * @author chuppch
 * @description 商品营销请求对象
 * @create 2025-05-24
 */
@Data
public class GoodsMarketRequestDTO {

    // 用户ID
    private String userId;
    // 渠道
    private String source;
    // 来源
    private String channel;
    // 商品ID
    private String goodsId;

}
