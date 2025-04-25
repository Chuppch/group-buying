package com.chuppch.domain.trade.service;

import com.chuppch.domain.trade.adapter.repository.ITradeRepository;
import com.chuppch.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.chuppch.domain.trade.model.entity.MarketPayOrderEntity;
import com.chuppch.domain.trade.model.entity.PayActivityEntity;
import com.chuppch.domain.trade.model.entity.PayDiscountEntity;
import com.chuppch.domain.trade.model.entity.UserEntity;
import com.chuppch.domain.trade.model.valobj.GroupBuyProgressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class TradeOrderService implements ITradeOrderService {
    @Resource
    private ITradeRepository repository;

    @Override
    public MarketPayOrderEntity queryNoPayMarketPayOrderByOutTradeNo(String userId, String outTradeNo) {
        log.info("拼团交易-查询未支付营销订单:{} outTradeNo:{}", userId, outTradeNo);
        return repository.queryMarketPayOrderEntityByOutTradeNo(userId, outTradeNo);
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        log.info("拼团交易-查询拼团进度:{}", teamId);
        return repository.queryGroupBuyProgress(teamId);
    }

    @Override
    public MarketPayOrderEntity lockMarketPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity) {
        log.info("拼团交易-锁单营销优惠支付订单:{} activityId:{} goodsId:{}", userEntity.getUserId(), payActivityEntity.getActivityId(), payDiscountEntity.getGoodsId());

        //构建聚合对象
        GroupBuyOrderAggregate groupBuyOrderAggregate = GroupBuyOrderAggregate.builder()
                .userEntity(userEntity)
                .payActivityEntity(payActivityEntity)
                .payDiscountEntity(payDiscountEntity)
                .build();

        // 锁单聚合对象 - 这回用户下单还没有支付 后续会有两个流程：支付成功 超时未支付（回退）
        return repository.lockMarketPayOrder(groupBuyOrderAggregate);
    }

}
