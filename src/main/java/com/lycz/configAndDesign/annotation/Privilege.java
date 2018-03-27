package com.lycz.configAndDesign.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注解标记的方法需要有相应权限才能使用
 * <p>
 * methodName 方法名称，建议填写，用于记录日志
 * privilegeLevel 权限等级
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Privilege {

    //权限等级(注解类的成员默认是 public static final 修饰)
    int NO_LOGIN = 0;  //"不需要登录
    int NEED_LOGIN = 1;  //"登录后可用
    int EE_TYPE = 2;  //"考生、系统管理员可用
    int ER_TYPE = 3;  //"考官、系统管理员可用
    int SYS_TYPE = 4;  //"仅系统管理员可用

    String methodName() default "";

    int privilegeLevel() default Privilege.NEED_LOGIN;
}
