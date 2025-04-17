package com.chuppch.domain.tag.service;

import com.chuppch.domain.tag.adapter.repository.ITagRepository;
import com.chuppch.domain.tag.model.entity.CrowdTagsJobEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TagService implements ITagService{

    @Resource
    private ITagRepository repository;

    @Override
    public void execTagBatchJob(String tagId, String batchId) {

        //1.查询批次任务
        CrowdTagsJobEntity crowdTagsJobEntity = repository.queryCrowdTagsJobEntity(tagId, batchId);

        //2.采集用户数据 - 这部分需要采集用户的消费数据，后续有用户发起拼单后再处理

        //3.数据写入记录
        List<String> userIdList = new ArrayList<String>(){{
            add("test3");
            add("test4");
        }};

        //4.一般人群标签的处理在公司中，会有专门的数据数仓团体通过脚本方式写入到数据库，就不哦那个这样一个一个或者批次写
        for (String userId : userIdList){
            repository.addCrowdTagsUserId(tagId,userId);
        }

        //5.更新人群标签统计量
        repository.updateCrowdTagsStatistics(tagId,userIdList.size());
    }
}
