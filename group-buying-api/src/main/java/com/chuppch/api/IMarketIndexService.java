package com.chuppch.api;

import com.chuppch.api.dto.GoodsMarketRequestDTO;
import com.chuppch.api.dto.GoodsMarketResponseDTO;
import com.chuppch.api.response.Response;

/**
 * @author chuppch
 * @description 用户拼单明细
 * @create 2025-05-24
 */
public interface IMarketIndexService {

    /**
     * 查询拼团营销配置
     *
     * @param goodsMarketRequestDTO 营销商品信息
     * @return 营销配置信息
     */
    Response<GoodsMarketResponseDTO> queryGroupBuyMarketConfig(GoodsMarketRequestDTO goodsMarketRequestDTO);

}
