package com.chuppch.domain.trade.model.aggregate;

import com.chuppch.domain.trade.model.entity.PayActivityEntity;
import com.chuppch.domain.trade.model.entity.PayDiscountEntity;
import com.chuppch.domain.trade.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyOrderAggregate {

    /** 用户实体对象 */
    private PayActivityEntity payActivityEntity;
    /** 支付活动实体对象 */
    private PayDiscountEntity payDiscountEntity;
    /** 支付优惠实体对象 */
    private UserEntity userEntity;
    /** 已参与拼团量 */
    private Integer userTakeOrderCount;

}
