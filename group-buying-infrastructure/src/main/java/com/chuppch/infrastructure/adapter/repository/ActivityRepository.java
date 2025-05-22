package com.chuppch.infrastructure.adapter.repository;

import com.chuppch.domain.activity.adapter.repository.IActivityRepository;
import com.chuppch.domain.activity.model.valobj.DiscountTypeEnum;
import com.chuppch.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.chuppch.domain.activity.model.valobj.SCSkuActivityVO;
import com.chuppch.domain.activity.model.valobj.SkuVO;
import com.chuppch.domain.trade.model.entity.NotifyTaskEntity;
import com.chuppch.infrastructure.dao.IGroupBuyActivityDao;
import com.chuppch.infrastructure.dao.IGroupBuyDiscountDao;
import com.chuppch.infrastructure.dao.ISCSkuActivityDao;
import com.chuppch.infrastructure.dao.ISkuDao;
import com.chuppch.infrastructure.dao.po.GroupBuyActivity;
import com.chuppch.infrastructure.dao.po.GroupBuyDiscount;
import com.chuppch.infrastructure.dao.po.SCSkuActivity;
import com.chuppch.infrastructure.dao.po.Sku;
import com.chuppch.infrastructure.dcc.DCCService;
import com.chuppch.infrastructure.redis.RedissonService;
import org.redisson.api.RBitSet;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chuppch
 * @description 活动仓储
 * @create 2025-4-16
 */
@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;

    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;

    @Resource
    private ISkuDao skuDao;

    @Resource
    private ISCSkuActivityDao skuActivityDao;

    @Resource
    private RedissonService redissonService;

    @Resource
    private DCCService dccService;

    @Override
    public GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId) {
        GroupBuyActivity groupBuyActivityRes = groupBuyActivityDao.queryValidGroupBuyActivityId(activityId);
        if (null == groupBuyActivityRes) return null;

        String discountId = groupBuyActivityRes.getDiscountId();

        GroupBuyDiscount groupBuyDiscountRes = groupBuyDiscountDao.queryGroupBuyActivityDiscountByDiscountId(discountId);
        if (null == groupBuyDiscountRes) return null;

        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = GroupBuyActivityDiscountVO.GroupBuyDiscount.builder()
                .discountName(groupBuyDiscountRes.getDiscountName())
                .discountDesc(groupBuyDiscountRes.getDiscountDesc())
                .discountType(DiscountTypeEnum.get(groupBuyDiscountRes.getDiscountType()))
                .marketPlan(groupBuyDiscountRes.getMarketPlan())
                .marketExpr(groupBuyDiscountRes.getMarketExpr())
                .tagId(groupBuyDiscountRes.getTagId())
                .build();

        return GroupBuyActivityDiscountVO.builder()
                .activityId(groupBuyActivityRes.getActivityId())
                .activityName(groupBuyActivityRes.getActivityName())
                .groupBuyDiscount(groupBuyDiscount)
                .groupType(groupBuyActivityRes.getGroupType())
                .takeLimitCount(groupBuyActivityRes.getTakeLimitCount())
                .target(groupBuyActivityRes.getTarget())
                .validTime(groupBuyActivityRes.getValidTime())
                .status(groupBuyActivityRes.getStatus())
                .startTime(groupBuyActivityRes.getStartTime())
                .endTime(groupBuyActivityRes.getEndTime())
                .tagId(groupBuyActivityRes.getTagId())
                .tagScope(groupBuyActivityRes.getTagScope())
                .build();
    }

    @Override
    public SkuVO querySkuByGoodsId(String goodsId) {
        Sku sku = skuDao.querySkuGoodsId(goodsId);
        if (null == sku) return null;
        return SkuVO.builder()
                .goodsId(sku.getGoodsId())
                .goodsName(sku.getGoodsName())
                .originalPrice(sku.getOriginalPrice())
                .build();
    }

    @Override
    public SCSkuActivityVO querySkuActivityByGoodsId(String source, String channel, String goodsId) {
        SCSkuActivity scSkuActivityReq = new SCSkuActivity();
        scSkuActivityReq.setSource(source);
        scSkuActivityReq.setChannel(channel);
        scSkuActivityReq.setGoodsId(goodsId);

        SCSkuActivity scSkuActivity = skuActivityDao.querySCSkuActivityBySCGoodsId(scSkuActivityReq);
        if (null == scSkuActivity) return null;

        return SCSkuActivityVO.builder()
                .source(scSkuActivity.getSource())
                .channel(scSkuActivity.getChannel())
                .activityId(scSkuActivity.getActivityId())
                .goodsId(scSkuActivity.getGoodsId())
                .build();

    }

    @Override
    public boolean isTagCrowdRange(String tagId, String userId) {
        RBitSet bitSet = redissonService.getBitSet(tagId);
        if (!bitSet.isExists()) return true;
        //判断用户是否存在于人群
        return bitSet.get(redissonService.getIndexFromUserId(userId));//这段代码中的方法的作用是判断某个用户是否属于某个标签（tagId）所定义的人群范围。
    }

    @Override
    public boolean downgradeSwitch() {
        return dccService.isDowngradeSwitch();
    }

    @Override
    public boolean cutRange(String userId) {
        return dccService.isCutRange(userId);
    }

}
