package com.chuppch.test.infrastructure.dao;

import com.alibaba.fastjson.JSON;
import com.chuppch.infrastructure.dao.IGroupBuyActivityDao;
import com.chuppch.infrastructure.dao.po.GroupBuyActivity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupBuyActivityDaoTest {

    @Resource
    private IGroupBuyActivityDao iGroupBuyActivityDao;

    @Test
    public void test_queryGroupBuyActivitty(){
        List<GroupBuyActivity> groupBuyActivities = iGroupBuyActivityDao.queryGroupBuyActivityList();
        log.info("测试结果:{}", JSON.toJSONString(groupBuyActivities));
    }

}
