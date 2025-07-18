package com.chuppch.infrastructure.dao;


import com.chuppch.infrastructure.dao.po.GroupBuyActivity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Chuppch
 * @description 拼团活动Dao
 * @create 2024-12-07 10:10
 */
@Mapper
public interface IGroupBuyActivityDao {

    List<GroupBuyActivity> queryGroupBuyActivityList();

    GroupBuyActivity queryValidGroupBuyActivity(GroupBuyActivity groupBuyActivityReq);

    GroupBuyActivity queryValidGroupBuyActivityId(Long activityId);

    //    GroupBuyActivity queryGroupBuyActivityByActivityId(Long activityId);
    GroupBuyActivity queryGroupBuyActivityByActivityId(Long activityId);
}
