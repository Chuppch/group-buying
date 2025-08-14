package com.chuppch.infrastructure.dcc;

import com.chuppch.types.annotations.DCCValue;
import com.chuppch.types.common.Constants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author chuppch
 * @description 动态配置服务
 * @create 2025-4-23
 */
@Service
public class DCCService {

    //定义两个属性 - 降级 and 切量
    @DCCValue("downgradeSwitch:0")
    private String downgradeSwitch;

    @DCCValue("cutRange:100")
    private String cutRange;

    @DCCValue("scBlacklist:s02c02")
    private String scBlacklist;

    //定义 降级 and 切量 两个判断
    public boolean isDowngradeSwitch() {
        return "1".equals(downgradeSwitch);
    }

    public boolean isCutRange(String userId) {
        // 计算哈希码的绝对值
        int hashCode = Math.abs(userId.hashCode());
        // 获取最后两位
        int lastTwoDigits = hashCode % 100;
        //判断是否在切量范围内
        if (lastTwoDigits <= Integer.parseInt(cutRange)){
            return true;
        }
        return false;
    }

    /**
     * 判断黑名单拦截渠道，true 拦截、false 放行
     */
    public boolean isSCBlackIntercept(String source, String channel) {
        List<String> list = Arrays.asList(scBlacklist.split(Constants.SPLIT));
        return list.contains(source + channel);
    }

}
