package com.chuppch.domain.activity.model.valobj;

import com.chuppch.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @author chuppch
 * @description 拼团活动营销配置值对象
 * @create 2025-4-16
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyActivityDiscountVO {

    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 来源
     */
    private String source;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 折扣对象
     */
    private GroupBuyDiscount groupBuyDiscount;
    /**
     * 拼团方式（0自动成团、1达成目标拼团）
     */
    private Integer groupType;
    /**
     * 拼团次数限制
     */
    private Integer takeLimitCount;
    /**
     * 拼团目标
     */
    private Integer target;
    /**
     * 拼团时长（分钟）
     */
    private Integer validTime;
    /**
     * 活动状态（0创建、1生效、2过期、3废弃）
     */
    private Integer status;
    /**
     * 活动开始时间
     */
    private Date startTime;
    /**
     * 活动结束时间
     */
    private Date endTime;
    /**
     * 人群标签规则标识
     */
    private String tagId;
    /**
     * 人群标签规则范围（多选；1可见限制、2参与限制）
     */
    private String tagScope;

    public boolean isVisible(){
        if (StringUtils.isBlank(this.tagScope)) return TagScopeEnumVO.VISIBLE.getAllow();
        String[] split = this.tagScope.split(Constants.SPLIT);
        if (split.length > 0 && Objects.equals(split[0], "1") && StringUtils.isBlank(split[0])){
            return TagScopeEnumVO.VISIBLE.getRefuse();
        }
        return TagScopeEnumVO.VISIBLE.getAllow();
    }

    public boolean isEable(){
        if (StringUtils.isBlank(this.tagScope)) return TagScopeEnumVO.ENABLE.getAllow();
        String[] split = this.tagScope.split(Constants.SPLIT);
        if (split.length == 2&& Objects.equals(split[1], "2") && StringUtils.isNoneBlank(split[1])){
            return TagScopeEnumVO.ENABLE.getRefuse();
        }
        return TagScopeEnumVO.ENABLE.getAllow();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupBuyDiscount{

        /**
         * 折扣标题
         */
        private String discountName;
        /**
         * 折扣描述
         */
        private String discountDesc;
        /**
         * 折扣类型（0:base、1:tag）
         */
        private DiscountTypeEnum discountType;
        /**
         * 营销优惠计划（ZJ:直减、MJ:满减、N元购）
         */
        private String marketPlan;
        /**
         * 营销优惠表达式
         */
        private String marketExpr;
        /**
         * 特定优惠限定
         */
        private String tagId;
    }


}
