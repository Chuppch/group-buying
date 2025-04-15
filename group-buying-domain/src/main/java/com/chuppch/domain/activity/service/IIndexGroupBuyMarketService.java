package com.chuppch.domain.activity.service;

import com.chuppch.domain.activity.model.entity.MarketProductEntity;
import com.chuppch.domain.activity.model.entity.TrialBalanceEntity;

/**
 * @author chuppch
 * @date 2022/04/01
 * @desc 首页营销服务接口
 */
public interface IIndexGroupBuyMarketService {

    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;

}
