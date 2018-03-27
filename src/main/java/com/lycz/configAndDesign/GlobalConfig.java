package com.lycz.configAndDesign;

public enum GlobalConfig {

    //系统日志相关
    LOG_SYSTEM("0", "日志等级-系统日志"),
    LOG_ERROR("1", "日志等级-错误"),
    LOG_SERIOUS("2", "日志等级-严重"),

    //最后需要一个分号
    ;

    private Object value;
    private String description;

    GlobalConfig(Object value) {
        this.value = value;
    }

    GlobalConfig(Object value, String description) {
        this.value = value;
        this.description = description;
    }

    public Object getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
