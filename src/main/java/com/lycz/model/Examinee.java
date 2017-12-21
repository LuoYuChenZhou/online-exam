package com.lycz.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "examinee")
public class Examinee {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 用户名
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 登录密码
     */
    @Column(name = "login_pwd")
    private String loginPwd;

    /**
     * 考生号、学号等
     */
    @Column(name = "ee_no")
    private String eeNo;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 联系电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 电子邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 排序号，越大排越前
     */
    @Column(name = "sort_no")
    private String sortNo;

    @Column(name = "reg_time")
    private Date regTime;

    /**
     * 状态（0-禁用，1-正常，4-删除）
     */
    private String status;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return login_name - 用户名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置用户名
     *
     * @param loginName 用户名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取登录密码
     *
     * @return login_pwd - 登录密码
     */
    public String getLoginPwd() {
        return loginPwd;
    }

    /**
     * 设置登录密码
     *
     * @param loginPwd 登录密码
     */
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    /**
     * 获取考生号、学号等
     *
     * @return ee_no - 考生号、学号等
     */
    public String getEeNo() {
        return eeNo;
    }

    /**
     * 设置考生号、学号等
     *
     * @param eeNo 考生号、学号等
     */
    public void setEeNo(String eeNo) {
        this.eeNo = eeNo;
    }

    /**
     * 获取真实姓名
     *
     * @return real_name - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取联系电话
     *
     * @return phone - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取排序号，越大排越前
     *
     * @return sort_no - 排序号，越大排越前
     */
    public String getSortNo() {
        return sortNo;
    }

    /**
     * 设置排序号，越大排越前
     *
     * @param sortNo 排序号，越大排越前
     */
    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * @return reg_time
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * @param regTime
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * 获取状态（0-禁用，1-正常，4-删除）
     *
     * @return status - 状态（0-禁用，1-正常，4-删除）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态（0-禁用，1-正常，4-删除）
     *
     * @param status 状态（0-禁用，1-正常，4-删除）
     */
    public void setStatus(String status) {
        this.status = status;
    }
}