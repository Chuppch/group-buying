package com.chuppch.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chuppch
 * @create
 * @desc 商品信息
 */

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chuppch
 * @description 商品信息
 * @create 2025-4-16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sku {

    /** 自增ID*/
    private Long id;
    /** 渠道*/
    private String source;
    /** 来源*/
    private String channel;
    /** 商品ID*/
    private String goodsId;
    /** 商品名称*/
    private String goodsName;
    /** 商品价格*/
    private BigDecimal originalPrice;
    /** 创建时间*/
    private Date createTime;
    /** 更新时间*/
    private Date updateTime;
}
