package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.CrowdTagsJob;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: chuppch
 * @date: 2025-4-17
 * @desc: 人群任务标签
 */
@Mapper
public interface ICrowdTagsJobDao {

    CrowdTagsJob queryCrowdTagsJob(CrowdTagsJob crowdTagsJobReq);

}
