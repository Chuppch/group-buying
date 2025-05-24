package com.chuppch.domain.activity.service;

import com.chuppch.domain.activity.model.entity.MarketProductEntity;
import com.chuppch.domain.activity.model.entity.TrialBalanceEntity;
import com.chuppch.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import com.chuppch.domain.activity.model.valobj.TeamStatisticVO;

import java.util.List;

/**
 * @author chuppch
 * @date 2022/04/01
 * @desc 首页营销服务接口
 */
public interface IIndexGroupBuyMarketService {
    /**
     * 首页拼团试算
     *
     * 根据传入的市场商品信息，进行拼团试算操作，返回优惠信息、预估返利、价格等数据，
     * 通常用于首页展示拼团活动时的营销吸引力评估。
     *
     * @param marketProductEntity 市场商品实体信息（包含商品ID、活动ID、基础价格等）
     * @return 拼团试算结果（包括预估价格、优惠金额、返利等）
     * @throws Exception 异常处理，计算过程中可能抛出业务异常
     */
    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;

    /**
     * 查询进行中的拼团订单
     *
     * @param activityId  活动ID
     * @param userId      用户ID
     * @param ownerCount  个人数量
     * @param randomCount 随机数量
     * @return 用户拼团明细数据
     */
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailList(Long activityId, String userId, Integer ownerCount, Integer randomCount);

    /**
     * 活动拼团队伍总结
     *
     * @param activityId 活动ID
     * @return 队伍统计
     */
    TeamStatisticVO queryTeamStatisticByActivityId(Long activityId);


}
