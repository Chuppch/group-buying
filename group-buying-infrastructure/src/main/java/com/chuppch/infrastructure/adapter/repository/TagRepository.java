package com.chuppch.infrastructure.adapter.repository;

import com.chuppch.domain.tag.adapter.repository.ITagRepository;
import com.chuppch.domain.tag.model.entity.CrowdTagsJobEntity;
import com.chuppch.infrastructure.dao.ICrowdTagsDao;
import com.chuppch.infrastructure.dao.ICrowdTagsDetailDao;
import com.chuppch.infrastructure.dao.ICrowdTagsJobDao;
import com.chuppch.infrastructure.dao.IGroupBuyDiscountDao;
import com.chuppch.infrastructure.dao.po.CrowdTags;
import com.chuppch.infrastructure.dao.po.CrowdTagsDetail;
import com.chuppch.infrastructure.dao.po.CrowdTagsJob;
import com.chuppch.infrastructure.redis.IRedisService;
import com.chuppch.infrastructure.redis.RedissonService;
import org.redisson.api.RBitSet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class TagRepository implements ITagRepository {

    @Resource
    private ICrowdTagsDao crowdTagsDao;
    @Resource
    private ICrowdTagsDetailDao crowdTagsDetailDao;
    @Resource
    private ICrowdTagsJobDao crowdTagsJobDao;

    @Resource
    private IRedisService redisService;

    @Override
    public CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId) {
        CrowdTagsJob crowdTagsJobReq = new CrowdTagsJob();
        crowdTagsJobReq.setTagId(tagId);
        crowdTagsJobReq.setBatchId(batchId);

        CrowdTagsJob crowdTagsJobRes = crowdTagsJobDao.queryCrowdTagsJob(crowdTagsJobReq);
        if (null == crowdTagsJobRes) return null;

        return CrowdTagsJobEntity.builder()
                .tagType(crowdTagsJobRes.getTagType())
                .tagRule(crowdTagsJobRes.getTagRule())
                .statStartTime(crowdTagsJobRes.getStatStartTime())
                .statEndTime(crowdTagsJobRes.getStatEndTime())
                .build();
    }

    @Override
    public void addCrowdTagsUserId(String tagId, String userId) {
        CrowdTagsDetail crowdTagsDetailReq = new CrowdTagsDetail();
        crowdTagsDetailReq.setTagId(tagId);
        crowdTagsDetailReq.setUserId(userId);

        try{
            crowdTagsDetailDao.addCrowdTagsUserId(crowdTagsDetailReq);

            //获取BitSet
            RBitSet bitSet = redisService.getBitSet(tagId);
            bitSet.set(redisService.getIndexFromUserId(userId), true);

        }catch (DuplicateKeyException ignore){
            //忽略唯一索引冲突
        }

    }

    @Override
    public void updateCrowdTagsStatistics(String tagId, int count) {
        CrowdTags crowdTagsReq = new CrowdTags();
        crowdTagsReq.setTagId(tagId);
        crowdTagsReq.setStatistics(count);

        crowdTagsDao.updateCrowdTagsStatistics(crowdTagsReq);
    }
}
