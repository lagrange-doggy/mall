package com.cskaoyan.mall.config;

import java.lang.annotation.*;

/**
 * @author xujn
 * @version 1.0
 * @date 2020/7/2 16:23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String value() default  "";

    String level() default "";
}
