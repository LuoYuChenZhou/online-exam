package com.lycz.controller.common.aspect;

import com.lycz.controller.common.CommonResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
 * 　　　　　　　　 NoSuchMethodException,IOException,IndexOutOfBoundsException
 * 　　　　　　　　 以及springmvc自定义异常等，如下：
 * SpringMVC自定义异常对应的status code
 * Exception                       HTTP Status Code
 * ConversionNotSupportedException         500 (Internal Server Error)
 * HttpMessageNotWritableException         500 (Internal Server Error)
 * HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 * HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 * HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 * NoSuchRequestHandlingMethodException    404 (Not Found)
 * TypeMismatchException                   400 (Bad Request)
 * HttpMessageNotReadableException         400 (Bad Request)
 * MissingServletRequestParameterException 400 (Bad Request)
 *
 * @version 1.0
 * @data 2018/1/4 21:41
 */
@ControllerAdvice
public class ExceptionHandlers {

    private Logger log = org.apache.logging.log4j.LogManager.getLogger(ControllerLogsAspect.class.getName());
    private CommonResult<JSONArray> result = new CommonResult<>();

    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JSONObject runtimeExceptionHandler(RuntimeException runtimeException) {
        runtimeException.printStackTrace();
        log.error(runtimeException.getMessage());
        result.setStatus(1000);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("服务器运行时异常！");
        return JSONObject.fromObject(result);
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public JSONObject nullPointerExceptionHandler(NullPointerException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(1000);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("空指针异常！");
        return JSONObject.fromObject(result);
    }

    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public JSONObject classCastExceptionHandler(ClassCastException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(1002);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("类型转换异常！");
        return JSONObject.fromObject(result);
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public JSONObject iOExceptionHandler(IOException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(1003);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("IO出现异常！");
        return JSONObject.fromObject(result);
    }

    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public JSONObject noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(1004);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("未知方法异常！");
        return JSONObject.fromObject(result);
    }

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public JSONObject indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(1005);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("数组越界异常！");
        return JSONObject.fromObject(result);
    }

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public JSONObject requestNotReadable(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(400);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("未知错误信息！");
        return JSONObject.fromObject(result);
    }

    //类型不匹配
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public JSONObject requestTypeMismatch(TypeMismatchException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(400);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("类型不匹配！");
        return JSONObject.fromObject(result);
    }

    //类型不匹配
    @ExceptionHandler({NumberFormatException.class})
    @ResponseBody
    public JSONObject requestNumberFormat(NumberFormatException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(400);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("类型不匹配！");
        return JSONObject.fromObject(result);
    }

    //绑定异常
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public JSONObject requestBindException(BindException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());

        result.setStatus(400);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("数据绑定异常！请检查传入的参数");
        return JSONObject.fromObject(result);
    }

    //Servlet Request 缺少参数
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public JSONObject requestMissingServletRequest(MissingServletRequestParameterException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(400);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("Servlet Request 缺少参数！");
        return JSONObject.fromObject(result);
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public JSONObject request405(HttpRequestMethodNotSupportedException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(405);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("HTTP请求方法不支持！");
        return JSONObject.fromObject(result);
    }

    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public JSONObject request406(HttpMediaTypeNotAcceptableException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(406);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("HTTP媒体类型不可接受！");
        return JSONObject.fromObject(result);
    }

    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public JSONObject server500(RuntimeException runtimeException) {
        runtimeException.printStackTrace();
        result.setStatus(500);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg("HTTP消息不可写或转换不支持！");
        return JSONObject.fromObject(result);
    }

    //其他错误
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public JSONObject requestUnknown(Exception ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        result.setStatus(400);
        result.setData(JSONArray.fromObject("[]"));
        result.setMsg(ex.getMessage());
        return JSONObject.fromObject(result);
    }
}

