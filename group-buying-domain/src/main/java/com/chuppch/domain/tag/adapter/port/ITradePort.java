package com.chuppch.domain.tag.adapter.port;

import com.chuppch.domain.trade.model.entity.NotifyTaskEntity;

/**
 * @author chuppch
 * @description 交易接口服务接口
 * @create 2025-01-31 10:38
 */
public interface ITradePort {

    String groupBuyNotify(NotifyTaskEntity notifyTask) throws Exception;

}
