package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.CrowdTagsDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: chuppch
 * @date: 2025-4-17
 * @desc: 人群标签明细
 */
@Mapper
public interface ICrowdTagsDetailDao {

    void addCrowdTagsUserId(CrowdTagsDetail crowdTagsDetailReq);

}
