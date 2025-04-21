package com.chuppch.domain.activity.service.trial.factory;

import com.chuppch.domain.activity.model.entity.MarketProductEntity;
import com.chuppch.domain.activity.model.entity.TrialBalanceEntity;
import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.model.valobj.SkuVO;
import com.chuppch.domain.activity.service.trial.node.RootNode;
import com.chuppch.types.design.framework.tree.StrategyHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @description: 活动策略工厂
 * @author: chuppch
 * @create: 2025-04-15
 **/
@Service
public class DefaultActivityStrategyFactory {

    private final RootNode rootNode;

    public DefaultActivityStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity>strategyHandler(){
        return rootNode;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DynamicContext{
        //拼团活动营销配置值对象
        private GroupBuyActivityDiscountVO groupBuyActivityDiscountVO;
        //商品信息
        private SkuVO skuVO;
        //折扣后价格
        private BigDecimal deductionPrice;
        //活动可见性限制
        private boolean visible;
        //活动可参与性限制
        private boolean enable;
    }
}
