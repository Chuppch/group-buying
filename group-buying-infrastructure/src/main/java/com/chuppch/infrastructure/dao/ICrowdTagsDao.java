package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.CrowdTags;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: chuppch
 * @date: 2025-4-17
 * @desc: 人群标签
 */
@Mapper
public interface ICrowdTagsDao {

    void updateCrowdTagsStatistics(CrowdTags crowdTagsReq);

}
