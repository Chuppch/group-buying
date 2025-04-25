package com.chuppch.trigger.http;

import com.alibaba.fastjson.JSON;
import com.chuppch.api.IMarketTradeService;
import com.chuppch.api.dto.LockMarketPayOrderRequestDTO;
import com.chuppch.api.dto.LockMarketPayOrderResponseDTO;
import com.chuppch.api.response.Response;
import com.chuppch.domain.activity.model.entity.MarketProductEntity;
import com.chuppch.domain.activity.model.entity.TrialBalanceEntity;
import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.service.IIndexGroupBuyMarketService;
import com.chuppch.domain.trade.model.entity.MarketPayOrderEntity;
import com.chuppch.domain.trade.model.entity.PayActivityEntity;
import com.chuppch.domain.trade.model.entity.PayDiscountEntity;
import com.chuppch.domain.trade.model.entity.UserEntity;
import com.chuppch.domain.trade.model.valobj.GroupBuyProgressVO;
import com.chuppch.domain.trade.service.ITradeOrderService;
import com.chuppch.infrastructure.dao.po.GroupBuyOrder;
import com.chuppch.types.enums.ResponseCode;
import com.chuppch.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/gbm/trade/")
public class MarketTradeController implements IMarketTradeService {

    @Resource
    private IIndexGroupBuyMarketService indexGroupBuyMarketService;

    @Resource
    private ITradeOrderService tradeOrderService;


    @Override
    public Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO) {
        try {
            //参数
            String userId = lockMarketPayOrderRequestDTO.getUserId();
            Long activityId = lockMarketPayOrderRequestDTO.getActivityId();
            String goodsId = lockMarketPayOrderRequestDTO.getGoodsId();
            String source = lockMarketPayOrderRequestDTO.getSource();
            String channel = lockMarketPayOrderRequestDTO.getChannel();
            String outTradeNo = lockMarketPayOrderRequestDTO.getOutTradeNo();
            String teamId = lockMarketPayOrderRequestDTO.getTeamId();


            log.info("营销交易锁单:{} lockMarketPayOrderRequestDTO:{}", userId, JSON.toJSONString(lockMarketPayOrderRequestDTO));

            //判断传参是否为空
            if (StringUtils.isBlank(userId) || StringUtils.isBlank(goodsId) || StringUtils.isBlank(source) || StringUtils.isBlank(channel) || null == activityId) {
                return Response.<LockMarketPayOrderResponseDTO>builder()
                        //如果有值为空，则返回 存在非法参数
                        .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                        .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                        .build();
            }

            //查询 outTradeNo 是否已经存在交易记录
            MarketPayOrderEntity marketPayOrderEntity = tradeOrderService.queryNoPayMarketPayOrderByOutTradeNo(userId, outTradeNo);
            if (null != marketPayOrderEntity) {
                //判断出该用户已经存在锁定的单 无需进行再进行创建 直接返回 SUCCESS 即可
                LockMarketPayOrderResponseDTO lockMarketPayOrderResponseDTO = LockMarketPayOrderResponseDTO.builder()
                        .orderId(marketPayOrderEntity.getOrderId())
                        .deductionPrice(marketPayOrderEntity.getDeductionPrice())
                        .tradeOrderStatus(marketPayOrderEntity.getTradeOrderStatusEnumVO().getCode())
                        .build();

                log.info("交易锁单记录（存在）:{} marketPayOrderEntity:{}", userId, JSON.toJSONString(marketPayOrderEntity));
                return Response.<LockMarketPayOrderResponseDTO>builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .info(ResponseCode.SUCCESS.getInfo())
                        .data(lockMarketPayOrderResponseDTO)
                        .build();
            }

            //判断拼团锁单是否完成了目标
            if (null != teamId) {
                //如果存在 拼团信息 判断是否锁单 锁单直接返回结果
                GroupBuyProgressVO groupBuyProgressVO = tradeOrderService.queryGroupBuyProgress(teamId);
                if (null != groupBuyProgressVO && Objects.equals(groupBuyProgressVO.getCompleteCount(), groupBuyProgressVO.getLockCount())) {
                    log.info("交易锁单拦截-拼单目标已达成:{} {}", userId, teamId);
                    return Response.<LockMarketPayOrderResponseDTO>builder()
                            .code(ResponseCode.E0006.getCode())
                            .info(ResponseCode.E0006.getInfo())
                            .build();
                }
            }

            // 营销优惠试算
            TrialBalanceEntity trialBalanceEntity = indexGroupBuyMarketService.indexMarketTrial(MarketProductEntity.builder()
                    .userId(userId)
                    .source(source)
                    .channel(channel)
                    .goodsId(goodsId)
                    .activityId(activityId)
                    .build());

            GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = trialBalanceEntity.getGroupBuyActivityDiscountVO();

            // 锁单
            marketPayOrderEntity = tradeOrderService.lockMarketPayOrder(
                    UserEntity.builder().userId(userId).build(),
                    PayActivityEntity.builder()
                            .teamId(teamId)
                            .activityId(activityId)
                            .activityName(groupBuyActivityDiscountVO.getActivityName())
                            .startTime(groupBuyActivityDiscountVO.getStartTime())
                            .endTime(groupBuyActivityDiscountVO.getEndTime())
                            .targetCount(groupBuyActivityDiscountVO.getTarget())
                            .build(),
                    PayDiscountEntity.builder()
                            .source(source)
                            .channel(channel)
                            .goodsId(goodsId)
                            .goodsName(trialBalanceEntity.getGoodsName())
                            .originalPrice(trialBalanceEntity.getOriginalPrice())
                            .deductionPrice(trialBalanceEntity.getDeductionPrice())
                            .outTradeNo(outTradeNo)
                            .build());

            log.info("交易锁单记录(新):{} marketPayOrderEntity:{}", userId, JSON.toJSONString(marketPayOrderEntity));

            // 返回结果
            return Response.<LockMarketPayOrderResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(LockMarketPayOrderResponseDTO.builder()
                            .orderId(marketPayOrderEntity.getOrderId())
                            .deductionPrice(marketPayOrderEntity.getDeductionPrice())
                            .tradeOrderStatus(marketPayOrderEntity.getTradeOrderStatusEnumVO().getCode())
                            .build())
                    .build();

        } catch (AppException e) {
            log.error("营销交易锁单业务异常:{} LockMarketPayOrderRequestDTO:{}", lockMarketPayOrderRequestDTO.getUserId(), JSON.toJSONString(lockMarketPayOrderRequestDTO), e);
            return Response.<LockMarketPayOrderResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            {
                log.error("营销交易锁单服务失败:{} LockMarketPayOrderRequestDTO:{}", lockMarketPayOrderRequestDTO.getUserId(), JSON.toJSONString(lockMarketPayOrderRequestDTO), e);
                return Response.<LockMarketPayOrderResponseDTO>builder()
                        .code(ResponseCode.UN_ERROR.getCode())
                        .info(ResponseCode.UN_ERROR.getInfo())
                        .build();
            }
        }
    }
}
