package com.chuppch.domain.activity.service.trial.node;

import com.alibaba.fastjson.JSON;
import com.chuppch.domain.activity.model.entity.MarketProductEntity;
import com.chuppch.domain.activity.model.entity.TrialBalanceEntity;
import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.model.valobj.SkuVO;
import com.chuppch.domain.activity.service.discount.IDiscountCalculateService;
import com.chuppch.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.chuppch.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.chuppch.domain.activity.service.trial.thread.QueryGroupBuyActivityDiscountVOThreadTask;
import com.chuppch.domain.activity.service.trial.thread.QuerySkuVOFromDBThreadTask;
import com.chuppch.types.design.framework.tree.StrategyHandler;
import com.chuppch.types.enums.ResponseCode;
import com.chuppch.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author chuppch
 * @date 2025-04-15
 * @desc 营销优惠节点
 */
@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private Map<String, IDiscountCalculateService> discountCalculateServiceMap;

    @Resource
    private TagNode tagNode;

    @Resource
    private ErrorNode errorNode;

    @Override
    protected void multiTread(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        //异步查询活动查询
        QueryGroupBuyActivityDiscountVOThreadTask queryGroupBuyActivityDiscountVOThreadTask = new QueryGroupBuyActivityDiscountVOThreadTask(requestParameter.getSource(), requestParameter.getChannel(), requestParameter.getGoodsId(),repository);
        FutureTask<GroupBuyActivityDiscountVO> groupBuyActivityDiscountVOFutureTask = new FutureTask<>(queryGroupBuyActivityDiscountVOThreadTask);
        threadPoolExecutor.execute(groupBuyActivityDiscountVOFutureTask);

        //异步查询商品信息 - 在实际生产中，商品有同步库或者调用接口查询。这里暂时使用DB方式查询
        QuerySkuVOFromDBThreadTask querySkuVOFromDBThreadTask = new QuerySkuVOFromDBThreadTask(requestParameter.getGoodsId(), repository);
        FutureTask<SkuVO> skuVOFutureTask = new FutureTask<>(querySkuVOFromDBThreadTask);
        threadPoolExecutor.execute(skuVOFutureTask);

        // 写入上下文 - 对于一些复杂场景，获取数据的操作，有时候会在下N个节点获取，这样前置查询数据，可以提高接口响应效率
        dynamicContext.setGroupBuyActivityDiscountVO(groupBuyActivityDiscountVOFutureTask.get(timeout, TimeUnit.MINUTES));
        dynamicContext.setSkuVO(skuVOFutureTask.get(timeout, TimeUnit.MINUTES));

        log.info("拼团商品查询试算服务-MarketNode userId:{} requestParamenter:{}",requestParameter.getUserId(), JSON.toJSONString(requestParameter));
    }

    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-MarketNode userId:{} requestParameter:{}", requestParameter.getUserId(), JSON.toJSONString(requestParameter));

        // 获取上下文数据
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicContext.getGroupBuyActivityDiscountVO();
        if (null == groupBuyActivityDiscountVO) {
            return router(requestParameter, dynamicContext);
        }

        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = groupBuyActivityDiscountVO.getGroupBuyDiscount();
        SkuVO skuVO = dynamicContext.getSkuVO();
        if (null == groupBuyDiscount || null == skuVO) {
            return router(requestParameter, dynamicContext);
        }

        // 优惠试算
        IDiscountCalculateService discountCalculateService = discountCalculateServiceMap.get(groupBuyDiscount.getMarketPlan());
        if (null == discountCalculateService) {
            log.info("不存在{}类型的折扣计算服务，支持类型为:{}", groupBuyDiscount.getMarketPlan(), JSON.toJSONString(discountCalculateServiceMap.keySet()));
            throw new AppException(ResponseCode.E0001.getCode(), ResponseCode.E0001.getInfo());
        }

        // 折扣价格
        BigDecimal payPrice = discountCalculateService.calculate(requestParameter.getUserId(), skuVO.getOriginalPrice(), groupBuyDiscount);
        // 将商品原价与实际支付价格之间的差值（即用户享受的优惠金额）保存到上下文
        dynamicContext.setDeductionPrice(skuVO.getOriginalPrice().subtract(payPrice));
        // 将计算出的用户实际支付金额保存到上下文
        dynamicContext.setPayPrice(payPrice);

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        //不存在配置的拼团活动，走异常结点
        if (null == dynamicContext.getGroupBuyActivityDiscountVO() || null == dynamicContext.getSkuVO() || null == dynamicContext.getDeductionPrice()) {
            return errorNode;
        }

        return tagNode;
    }
}
