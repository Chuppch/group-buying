package com.chuppch.infrastructure.dao;

import com.chuppch.infrastructure.dao.po.NotifyTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chuppch
 * @description 回调任务
 * @create 2025-05-16
 */
@Mapper
public interface INotifyTaskDao {

    void insert(NotifyTask notifyTask);

}
