package com.lycz.controller.common;

import java.io.Serializable;

public class CommonResult<T> implements Serializable {
    private int status;
    private String msg;//用于页面提示的信息
    private String logMsg;//用于日志保存的信息（没有则记录msg）
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
