package com.chuppch.types.annotations;

import java.lang.annotation.*;

/**
 * @author chuppch
 * @description 注解，动态配置中心标记
 * @create 2025-4-23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DCCValue {

    String value() default "";

}
