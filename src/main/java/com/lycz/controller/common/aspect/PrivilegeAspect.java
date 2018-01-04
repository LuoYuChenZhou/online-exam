package com.lycz.controller.common.aspect;

import com.lycz.controller.common.CommonMethods;
import com.lycz.controller.common.CommonResult;
import com.lycz.controller.common.ToolUtil;
import com.lycz.controller.common.annotation.Privilege;
import com.lycz.service.base.TokenService;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/1/4 8:40
 */
@Aspect
@Component
public class PrivilegeAspect {

    private Logger log = LogManager.getLogger();

    @Autowired
    private TokenService tokenService;

    /**
     * 切点
     * 要求token放在最后
     * 虽然指定了argNames，但是无效，只要是String放在最后都会被token获取到
     */
    @Pointcut(value = "@annotation(com.lycz.controller.common.annotation.Privilege) && args(..,token)", argNames = "token")
    public void priAspect(String token) {
    }

    @Around(value = "priAspect(token)", argNames = "joinPoint,token")
    public Object doController(ProceedingJoinPoint joinPoint, String token) throws Throwable {
        CommonResult<JSONObject> result = new CommonResult<>();
        result.setMsg("权限不足");
        result.setData(JSONObject.fromObject("{}"));
        result.setStatus(401);

        log.info("什么鬼");

        Integer privilegeLevel = 0;//权限
        boolean canExe = false;//是否拥有权限执行

        //获取注解信息
        String currentClassName = joinPoint.getTarget().getClass().getName();
        Class<?> tempClass;
        try {
            tempClass = Class.forName(currentClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("权限类获取类信息失败");
            return JSONObject.fromObject(result);
        }
        Annotation[] annotations = tempClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Privilege.class) {
                Privilege privilege = (Privilege) annotation;
                privilegeLevel = privilege.privilegeLevel();
                break;
            }
        }

        //获取用户权限
        Map<String, Object> tokenMap = tokenService.getToken(token);
        if(ToolUtil.isEmpty(tokenMap)){
            result.setMsg("非法登录");
            return JSONObject.fromObject(result);
        }
        String tokenType = (String) tokenMap.get("userType");
        if (ToolUtil.isEmpty(tokenType)) {
            result.setMsg("非法登录");
            return JSONObject.fromObject(result);
        }
        String sys = CommonMethods.getProperty("config/sysLg.properties", "sys_user_type");
        switch (privilegeLevel) {
            case 0:
                canExe = true;
                break;
            case 1:
                if (ToolUtil.anyEqual(tokenType, "Examinee", "Examiner", sys)) {
                    canExe = true;
                }
                break;
            case 2:
                if (ToolUtil.anyEqual(tokenType, "Examinee", sys)) {
                    canExe = true;
                }
                break;
            case 3:
                if (ToolUtil.anyEqual(tokenType, "Examiner", sys)) {
                    canExe = true;
                }
                break;
            case 4:
                if (sys.equals(tokenType)) {
                    canExe = true;
                }
                break;
            default:
                result.setStatus(400);
                result.setMsg("方法权限错误");
                return JSONObject.fromObject(result);
        }

        if (canExe) {
            return joinPoint.proceed();
        } else {
            return JSONObject.fromObject(result);
        }

    }

}