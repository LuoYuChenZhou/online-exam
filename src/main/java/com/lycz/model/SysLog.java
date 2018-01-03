package com.lycz.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_log")
public class SysLog {
    /**
     * 主键id
     */
    @Id
    private String id;

    /**
     * 日志标题
     */
    @Column(name = "log_title")
    private String logTitle;

    /**
     * 日志等级(0-系统信息，1-错误，2-严重错误)
     */
    @Column(name = "log_level")
    private String logLevel;

    /**
     * 操作人id（系统管理员使用sys_id代替）
     */
    @Column(name = "log_user")
    private String logUser;

    /**
     * 模块名称
     */
    @Column(name = "module_name")
    private String moduleName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "log_description")
    private String logDescription;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取日志标题
     *
     * @return log_title - 日志标题
     */
    public String getLogTitle() {
        return logTitle;
    }

    /**
     * 设置日志标题
     *
     * @param logTitle 日志标题
     */
    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    /**
     * 获取日志等级(0-系统信息，1-错误，2-严重错误)
     *
     * @return log_level - 日志等级(0-系统信息，1-错误，2-严重错误)
     */
    public String getLogLevel() {
        return logLevel;
    }

    /**
     * 设置日志等级(0-系统信息，1-错误，2-严重错误)
     *
     * @param logLevel 日志等级(0-系统信息，1-错误，2-严重错误)
     */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * 获取操作人id（系统管理员使用sys_id代替）
     *
     * @return log_user - 操作人id（系统管理员使用sys_id代替）
     */
    public String getLogUser() {
        return logUser;
    }

    /**
     * 设置操作人id（系统管理员使用sys_id代替）
     *
     * @param logUser 操作人id（系统管理员使用sys_id代替）
     */
    public void setLogUser(String logUser) {
        this.logUser = logUser;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return log_description
     */
    public String getLogDescription() {
        return logDescription;
    }

    /**
     * @param logDescription
     */
    public void setLogDescription(String logDescription) {
        this.logDescription = logDescription;
    }

    public SysLog() {
    }

    public SysLog(String id, String logTitle, String logLevel, String logUser, Date createTime, String logDescription ,String moduleName) {
        this.id = id;
        this.logTitle = logTitle;
        this.logLevel = logLevel;
        this.logUser = logUser;
        this.createTime = createTime;
        this.logDescription = logDescription;
        this.moduleName = moduleName;
    }
}