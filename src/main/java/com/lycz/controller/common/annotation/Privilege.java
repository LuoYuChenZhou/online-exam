package com.lycz.controller.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解标记的方法需要有相应权限才能使用
 * <p>
 * methodName 方法名称，建议填写，用于记录日志
 * privilegeLevel 权限等级
 * 0：无限制
 * 1：登录后可用
 * 2：考生可用
 * 3：考官可用
 * 4：系统管理员可用
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Privilege {
    String methodName() default "";

    int privilegeLevel() default 1;
}
