package com.lycz.configAndDesign.aspect;

import com.lycz.configAndDesign.ToolUtil;
import com.lycz.configAndDesign.annotation.Privilege;
import com.lycz.configAndDesign.GlobalConfig;
import com.lycz.model.SysLog;
import com.lycz.service.base.SysLogService;
import com.lycz.service.base.TokenService;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 日志切片
 *
 * @author lizhenqing
 * @version 1.0
 */
@Aspect
@Component
public class ControllerLogsAspect {

    private Logger log = LogManager.getLogger(ControllerLogsAspect.class.getName());
    private String classMapping;  //类对应的@RequestMapping注解的value
    private String methodMapping;  //方法对应的@RequestMapping注解的value
    private String preTitle;  //方法对应的@Privilege注解的methodName
    private String userId;//当前操作人员

    private final SysLogService sysLogService;
    private final TokenService tokenService;

    @Autowired
    public ControllerLogsAspect(SysLogService sysLogService, TokenService tokenService) {
        this.sysLogService = sysLogService;
        this.tokenService = tokenService;
    }

    /**
     * 通用切点
     * 要求token放在最后
     * 虽然指定了argNames，但是无效，只要是String放在最后都会被token获取到
     */
    @Pointcut(value = "execution(* com.lycz.controller..*Controller.*(..)) " +
            "&& !@annotation(com.lycz.configAndDesign.annotation.NoSaveLog)" +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerAspect() {
    }

    @After(value = "controllerAspect()", argNames = "joinPoint")
    public void getInfo(JoinPoint joinPoint) {
        //获取token
        ServletRequestAttributes servletWebRequest = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletWebRequest.getRequest();
        String token = request.getParameter("token");

        try {
            //获取控制类上的注解
            String currentClassName = joinPoint.getTarget().getClass().getName();
            Class<?> tempClass = Class.forName(currentClassName);
            Annotation[] annotations = tempClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == RequestMapping.class) {
                    RequestMapping requestMapping = (RequestMapping) annotation;
                    classMapping = requestMapping.value()[0];
                    break;
                }
            }

            //获取方法上的注解
            String currentMethodName = joinPoint.getSignature().getName();
            Method[] methods = tempClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(currentMethodName)) {
                    Annotation[] annotations1 = method.getAnnotations();
                    for (Annotation annotation1 : annotations1) {
                        if (annotation1.annotationType() == RequestMapping.class) {
                            RequestMapping requestMapping = (RequestMapping) annotation1;
                            methodMapping = requestMapping.value()[0];
                        }
                        if (annotation1.annotationType() == Privilege.class) {
                            Privilege privilege = (Privilege) annotation1;
                            preTitle = privilege.methodName();
                        }
                    }
                    break;
                }
            }

            //获取当前操作人员
            Map<String, Object> tokenMap = tokenService.getTokenMap(token);
            if (ToolUtil.isEmpty(tokenMap)) {
                userId = "";
            } else {
                userId = (String) tokenMap.get("id");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("日志保存时获取类和方法信息失败{}", e.getMessage());
        }
    }

    @AfterReturning(value = "controllerAspect()", argNames = "rtv", returning = "rtv")
    public void saveErrLogs(Object rtv) {
        String level;
        Integer status = null;
        String msg = null;
        try {
            JSONObject jrtv = JSONObject.fromObject(rtv);
            if (!jrtv.has("status") || jrtv.get("status") == null) {
                status = (!jrtv.has("code") || null == jrtv.get("code")) ? null : jrtv.getInt("code");
            } else {
                status = jrtv.getInt("status");
            }

            if (null == jrtv.get("logMsg")) {
                if (null == jrtv.get("msg") || ToolUtil.isEmpty(jrtv.getString("msg"))) {
                    msg = "该错误没有返回msg";
                } else {
                    msg = jrtv.getString("msg");
                }
            } else {
                if (ToolUtil.isEmpty(jrtv.getString("logMsg"))) {
                    if (null == jrtv.get("msg") || ToolUtil.isEmpty(jrtv.getString("msg"))) {
                        msg = "该错误没有返回msg";
                    } else {
                        msg = jrtv.getString("msg");
                    }
                } else {
                    msg = jrtv.getString("logMsg");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null == status) {
            return;
        }
        //根据状态码判断严重等级
        if (status <= 204) {
            return;
        } else if (status < 500) {
            level = (String) GlobalConfig.LOG_ERROR.getValue();
        } else {
            level = (String) GlobalConfig.LOG_SERIOUS.getValue();
        }

        Date date = new Date();
        SysLog logsEntity = new SysLog(UUID.randomUUID().toString(), preTitle + "发生错误", level, userId, date,
                msg, classMapping + methodMapping);
        try {
            if (sysLogService.save(logsEntity) < 1) {
                log.error("日志保存失败：{}:{}", logsEntity.getModuleName(), msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("日志保存失败：{}{}:{}", classMapping, methodMapping, msg);
        }
    }

    @AfterThrowing(value = "controllerAspect()", argNames = "ex", throwing = "ex")
    public void exLogs(Exception ex) {
        String level = (String) GlobalConfig.LOG_SERIOUS.getValue();

        try {
            //处理堆栈信息
            List<StackTraceElement> list = Arrays.stream(ex.getStackTrace()).
                    filter(stack -> stack.toString().matches(".*com.lycz.*")).
                    filter(stack -> !stack.toString().matches(".*[$][$].*")).
                    collect(Collectors.toList());
            list.add(0, ex.getStackTrace()[0]);
            list = list.stream().distinct().collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement ste : list) {
                sb.append(ste.toString()).append("<br/>");
            }

            Date date = new Date();
            SysLog logsEntity = new SysLog(UUID.randomUUID().toString(), preTitle + "发生错误", level, userId, date,
                    sb.toString(), classMapping + methodMapping);
            if (sysLogService.save(logsEntity) < 1) {
                log.error("异常日志保存失败：{}:{}", logsEntity.getModuleName(), sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("异常日志保存失败{}{}:{}", classMapping, methodMapping, e.getMessage());
        }
    }

}
