package com.chuppch.api;

import com.chuppch.api.response.Response;

/**
 * @author chuppch
 * @description DCC 动态配置中心
 * @create 2025-04-23
 */
public interface IDCCService {

    Response<Boolean> updateConfig(String key, String value);

}
