package com.chuppch.api.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author chuppch
 * @description 结算请求对象
 * @create 2025-05-24
 */
@Data
public class SettlementMarketPayOrderRequestDTO {

    /** 渠道 */
    private String source;
    /** 来源 */
    private String channel;
    /** 用户ID */
    private String userId;
    /** 外部交易单号 */
    private String outTradeNo;
    /** 外部交易时间 */
    private Date outTradeTime;

}
