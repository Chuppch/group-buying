package com.chuppch.trigger.http;

import com.alibaba.fastjson.JSON;
import com.chuppch.api.IMarketTradeService;
import com.chuppch.api.dto.LockMarketPayOrderRequestDTO;
import com.chuppch.api.dto.LockMarketPayOrderResponseDTO;
import com.chuppch.api.dto.SettlementMarketPayOrderRequestDTO;
import com.chuppch.api.dto.SettlementMarketPayOrderResponseDTO;
import com.chuppch.api.response.Response;
import com.chuppch.domain.activity.model.entity.MarketProductEntity;
import com.chuppch.domain.activity.model.entity.TrialBalanceEntity;
import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.service.IIndexGroupBuyMarketService;
import com.chuppch.domain.trade.model.entity.*;
import com.chuppch.domain.trade.model.valobj.GroupBuyProgressVO;
import com.chuppch.domain.trade.service.ITradeLockOrderService;
import com.chuppch.domain.trade.service.ITradeSettlementOrderService;
import com.chuppch.types.enums.ResponseCode;
import com.chuppch.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author chuppch
 * @description 营销交易服务
 * @create 2025-05-22
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/gbm/trade/")
public class MarketTradeController implements IMarketTradeService {

    @Resource
    private IIndexGroupBuyMarketService indexGroupBuyMarketService;

    @Resource
    private ITradeLockOrderService tradeOrderService;

    @Resource
    private ITradeSettlementOrderService tradeSettlementOrderService;

    /**
     * 【营销交易锁单接口】
     * 用于用户在拼团活动中下单时锁定一笔订单（未支付），确保同一用户同一订单号不会重复下单。
     * 主要流程包括：
     * 1. 校验请求参数的合法性；
     * 2. 检查该用户该订单号是否已存在未支付订单，若存在直接返回，防止重复下单；
     * 3. 校验拼团是否已达成目标人数，若已达成则不允许继续下单；
     * 4. 进行优惠试算，获取商品的优惠信息；
     * 5. 校验用户是否在可参与人群范围内；
     * 6. 正式锁定订单，生成订单记录；
     * 7. 返回订单号、优惠金额、订单状态等信息。
     *
     * @param requestDTO 锁单请求参数
     * @return 锁单结果响应
     */
    @RequestMapping(value = "lock_market_pay_order", method = RequestMethod.POST)
    @Override
    public Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(@RequestBody LockMarketPayOrderRequestDTO requestDTO) {
        try {
            //参数
            String userId = requestDTO.getUserId();
            Long activityId = requestDTO.getActivityId();
            String goodsId = requestDTO.getGoodsId();
            String source = requestDTO.getSource();
            String channel = requestDTO.getChannel();
            String outTradeNo = requestDTO.getOutTradeNo();
            String teamId = requestDTO.getTeamId();
            String notifyUrl = requestDTO.getNotifyUrl();


            log.info("营销交易锁单:{} lockMarketPayOrderRequestDTO:{}", userId, JSON.toJSONString(requestDTO));

            //判断传参是否为空
            if (StringUtils.isBlank(userId) || StringUtils.isBlank(source) || StringUtils.isBlank(channel) || StringUtils.isBlank(goodsId) || StringUtils.isBlank(goodsId) || null == activityId || StringUtils.isBlank(notifyUrl)) {
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
            if (StringUtils.isNoneBlank(teamId) ) {
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

            // 人群限定
            if (!trialBalanceEntity.getIsVisible() || !trialBalanceEntity.getIsEnable()) {
                return Response.<LockMarketPayOrderResponseDTO>builder()
                        .code(ResponseCode.E0007.getCode())
                        .info(ResponseCode.E0007.getInfo())
                        .build();
            }

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
                            .validTime(groupBuyActivityDiscountVO.getValidTime())
                            .targetCount(groupBuyActivityDiscountVO.getTarget())
                            .build(),
                    PayDiscountEntity.builder()
                            .source(source)
                            .channel(channel)
                            .goodsId(goodsId)
                            .goodsName(trialBalanceEntity.getGoodsName())
                            .originalPrice(trialBalanceEntity.getOriginalPrice())
                            .deductionPrice(trialBalanceEntity.getDeductionPrice())
                            .payPrice(trialBalanceEntity.getPayPrice())
                            .outTradeNo(outTradeNo)
                            .notifyUrl(notifyUrl)
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
            log.error("营销交易锁单业务异常:{} LockMarketPayOrderRequestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<LockMarketPayOrderResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            {
                log.error("营销交易锁单服务失败:{} LockMarketPayOrderRequestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
                return Response.<LockMarketPayOrderResponseDTO>builder()
                        .code(ResponseCode.UN_ERROR.getCode())
                        .info(ResponseCode.UN_ERROR.getInfo())
                        .build();
            }
        }
    }

    /**
     * 【营销交易结算接口】
     * 用于用户支付成功后，对拼团订单进行结算处理。
     * 主要流程包括：
     * 1. 校验请求参数的合法性；
     * 2. 调用结算服务，完成订单结算（如更新订单状态、发放奖励等）；
     * 3. 返回结算后的用户ID、团队ID、活动ID、订单号等信息。
     *
     * 典型场景：支付成功后由前端或支付回调服务调用，完成拼团订单的最终结算。
     *
     * @param requestDTO 结算请求参数
     * @return 结算结果响应
     */
    @RequestMapping(value = "settlement_market_pay_order", method = RequestMethod.POST)
    @Override
    public Response<SettlementMarketPayOrderResponseDTO> settlementMarketPayOrder(@RequestBody SettlementMarketPayOrderRequestDTO requestDTO) {
        try {
            log.info("营销交易组队结算开始:{} outTradeNo:{}", requestDTO.getUserId(), requestDTO.getOutTradeNo());

            if (StringUtils.isBlank(requestDTO.getUserId()) || StringUtils.isBlank(requestDTO.getSource()) || StringUtils.isBlank(requestDTO.getChannel()) || StringUtils.isBlank(requestDTO.getOutTradeNo()) || null == requestDTO.getOutTradeTime()) {
                return Response.<SettlementMarketPayOrderResponseDTO>builder()
                        .code(ResponseCode.ILLEGAL_PARAMETER.getCode())
                        .info(ResponseCode.ILLEGAL_PARAMETER.getInfo())
                        .build();
            }

            // 1. 结算服务
            TradePaySettlementEntity tradePaySettlementEntity = tradeSettlementOrderService.settlementMarketPayOrder(TradePaySuccessEntity.builder()
                    .source(requestDTO.getSource())
                    .channel(requestDTO.getChannel())
                    .userId(requestDTO.getUserId())
                    .outTradeNo(requestDTO.getOutTradeNo())
                    .outTradeTime(requestDTO.getOutTradeTime())
                    .build());

            SettlementMarketPayOrderResponseDTO responseDTO = SettlementMarketPayOrderResponseDTO.builder()
                    .userId(tradePaySettlementEntity.getUserId())
                    .teamId(tradePaySettlementEntity.getTeamId())
                    .activityId(tradePaySettlementEntity.getActivityId())
                    .outTradeNo(tradePaySettlementEntity.getOutTradeNo())
                    .build();

            // 返回结果
            Response<SettlementMarketPayOrderResponseDTO> response = Response.<SettlementMarketPayOrderResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(responseDTO)
                    .build();

            log.info("营销交易组队结算完成:{} outTradeNo:{} response:{}", requestDTO.getUserId(), requestDTO.getOutTradeNo(), JSON.toJSONString(response));

            return response;
        } catch (AppException e) {
            log.error("营销交易组队结算异常:{} LockMarketPayOrderRequestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<SettlementMarketPayOrderResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        }catch (Exception e) {
            log.error("营销交易组队结算失败:{} LockMarketPayOrderRequestDTO:{}", requestDTO.getUserId(), JSON.toJSONString(requestDTO), e);
            return Response.<SettlementMarketPayOrderResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
