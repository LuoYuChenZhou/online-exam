//package com.lycz.controller.common.aspect;
//
//import com.lycz.controller.common.GlobalConfig;
//import com.lycz.controller.common.annotation.Privilege;
//import com.lycz.model.SysLog;
//import net.sf.json.JSONObject;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import tk.mybatis.mapper.util.StringUtil;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Aspect
//@Component
//public class ControllerLogsAspect {
//
//    private Logger log = LogManager.getLogger(ControllerLogsAspect.class.getName());
//    private String classMapping;  //类对应的@RequestMapping注解的value
//    private String methodMapping;  //方法对应的@RequestMapping注解的value
//    private String preTitle;  //方法对应的@ApiOperation注解的value
//    private String currentClassName;  //当前类名称
//    private String currentMethodName;  //当前方法名称
//
//    /**
//     * 通用切点
//     * 要求token放在最后
//     * 虽然指定了argNames，但是无效，只要是String放在最后都会被token获取到
//     */
//    @Pointcut(value = "execution(* com.lycz.controller..*Controller.*(..)) " +
//            "&& !execution(* com.lycz.controller.common..*.*(..)) " +
//            "&& !@annotation(com.lycz.controller.common.annotation.NoSaveLog)" +
//            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)" +
//            "&& args(..,token)", argNames = "token")
//    public void controllerAspect(String token) {
//    }
//
//    @After(value = "controllerAspect(token)", argNames = "joinPoint,token")
//    public void getClassMethodsInfo(JoinPoint joinPoint, String token) {
//        try {
//            //获取控制类上的注解
//            currentClassName = joinPoint.getTarget().getClass().getName();
//            Class<?> tempClass = Class.forName(currentClassName);
//            Annotation[] annotations = tempClass.getAnnotations();
//            for (Annotation annotation : annotations) {
//                if (annotation.annotationType() == RequestMapping.class) {
//                    RequestMapping requestMapping = (RequestMapping) annotation;
//                    classMapping = requestMapping.value()[0];
//                    break;
//                }
//            }
//
//            //获取方法上的注解
//            currentMethodName = joinPoint.getSignature().getName();
//            Method[] methods = tempClass.getMethods();
//            for (Method method : methods) {
//                if (method.getName().equals(currentMethodName)) {
//                    Annotation[] annotations1 = method.getAnnotations();
//                    for (Annotation annotation1 : annotations1) {
//                        if (annotation1.annotationType() == RequestMapping.class) {
//                            RequestMapping requestMapping = (RequestMapping) annotation1;
//                            methodMapping = requestMapping.value()[0];
//                        }
//                        if (annotation1.annotationType() == Privilege.class) {
//                            Privilege privilege = (Privilege) annotation1;
//                            preTitle = privilege.methodName();
//                        }
//                    }
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            log.error("日志保存时获取类和方法信息失败{}", e.getMessage());
//        }
//    }
//
//    @AfterReturning(value = "controllerAspect(token) ", argNames = "rtv,token", returning = "rtv")
//    public void saveErrLogs(Object rtv, String token) {
//        String level;
//        ServletRequestAttributes servletWebRequest = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = servletWebRequest.getRequest();
//        Integer status = null;
//        String msg = null;
//        try {
//            JSONObject jrtv = JSONObject.fromObject(rtv);
//            status = null == jrtv.getString("status") ? null : jrtv.getInt("status");
//            msg = StringUtil.isEmpty(jrtv.getString("msg")) ? "该错误没有返回msg" : jrtv.getString("msg");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (null == status) {
//            return;
//        }
//        //根据状态码判断严重等级
//        if (status <= 204) {
//            return;
//        } else if (status < 500) {
//            level = (String) GlobalConfig.LOG_ERROR.getValue();
//        } else {
//            level = (String) GlobalConfig.LOG_SERIOUS.getValue();
//        }
//
//        SysLog logsEntity = new SysLog(UUID.randomUUID().toString(),preTitle+"发生错误",level,);
//        try {
//            if (!SystemLogs.saveSysLog(logsEntity, token)) {
//                log.error("日志保存失败：" + logsEntity.getModuleName() + ":" + msg);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("日志保存失败：" + classMapping + methodMapping + ":" + msg);
//        }
//    }
//
//    @AfterThrowing(value = "controllerAspect(token)", argNames = "token,ex", throwing = "ex")
//    public void exLogs(String token, Exception ex) {
//        String level = (String) GlobalConfig.LOG_SERIOUS.getValue();
//        ServletRequestAttributes servletWebRequest = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = servletWebRequest.getRequest();
//
//        try {
//            //处理堆栈信息
//            List<StackTraceElement> list = Arrays.stream(ex.getStackTrace()).
//                    filter(stack -> stack.toString().matches(".*com.lycz.*")).
//                    filter(stack -> !stack.toString().matches(".*[$][$].*")).
//                    collect(Collectors.toList());
//            list.add(0, ex.getStackTrace()[0]);
//            list = list.stream().distinct().collect(Collectors.toList());
//            StringBuilder sb = new StringBuilder();
//            for (StackTraceElement ste : list) {
//                sb.append(ste.toString()).append("<br/>");
//            }
//
//            String clientIp = "";
//            try {
//                clientIp = request.getParameter("clientIP");
//            } catch (Exception es) {
//                //不做处理
//            }
//            SysLog logsEntity = new SysLog();
//            if (!SystemLogs.saveSysLog(logsEntity, token)) {
//                log.error("异常日志保存失败：" + logsEntity.getModuleName());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("异常日志保存失败：" + classMapping + methodMapping);
//        }
//    }
//
//}
