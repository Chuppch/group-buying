package com.chuppch.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author chuppch
 * @date 2025-4-21
 * @desc 活动人群标签作用域范围枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TagScopeEnumVO {

    VISIBLE(true, false, "是否可看见拼团"),
    ENABLE(true,false,"是否可参与拼团"),
    ;

    private Boolean allow;
    private Boolean refuse;
    private String desc;

}
