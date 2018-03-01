package com.lycz.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "er_ee")
public class ErEe {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 考官id
     */
    @Column(name = "examiner_id")
    private String examinerId;

    /**
     * 考生id
     */
    @Column(name = "examinee_id")
    private String examineeId;

    /**
     * 当前状态（1考生请求加入、2考官邀请加入、3考生考官关系、4考生退出关系、5考官踢出关系）
     */
    @Column(name = "cur_status")
    private String curStatus;

    /**
     * 历史状态，以下划线隔开，如：1_3_5
     */
    @Column(name = "his_status")
    private String hisStatus;

    /**
     * 历史时间，以_隔开，存入毫秒数
     */
    @Column(name = "his_time")
    private String hisTime;

    /**
     * 最终操作时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

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
     * 获取考官id
     *
     * @return examiner_id - 考官id
     */
    public String getExaminerId() {
        return examinerId;
    }

    /**
     * 设置考官id
     *
     * @param examinerId 考官id
     */
    public void setExaminerId(String examinerId) {
        this.examinerId = examinerId;
    }

    /**
     * 获取考生id
     *
     * @return examinee_id - 考生id
     */
    public String getExamineeId() {
        return examineeId;
    }

    /**
     * 设置考生id
     *
     * @param examineeId 考生id
     */
    public void setExamineeId(String examineeId) {
        this.examineeId = examineeId;
    }

    /**
     * 获取当前状态（1考生请求加入、2考官邀请加入、3考生考官关系、4考生退出关系、5考官踢出关系）
     *
     * @return cur_status - 当前状态（1考生请求加入、2考官邀请加入、3考生考官关系、4考生退出关系、5考官踢出关系）
     */
    public String getCurStatus() {
        return curStatus;
    }

    /**
     * 设置当前状态（1考生请求加入、2考官邀请加入、3考生考官关系、4考生退出关系、5考官踢出关系）
     *
     * @param curStatus 当前状态（1考生请求加入、2考官邀请加入、3考生考官关系、4考生退出关系、5考官踢出关系）
     */
    public void setCurStatus(String curStatus) {
        this.curStatus = curStatus;
    }

    /**
     * 获取历史状态，以下划线隔开，如：1_3_5
     *
     * @return his_status - 历史状态，以下划线隔开，如：1_3_5
     */
    public String getHisStatus() {
        return hisStatus;
    }

    /**
     * 设置历史状态，以下划线隔开，如：1_3_5
     *
     * @param hisStatus 历史状态，以下划线隔开，如：1_3_5
     */
    public void setHisStatus(String hisStatus) {
        this.hisStatus = hisStatus;
    }

    /**
     * 获取历史时间，以_隔开，存入毫秒数
     *
     * @return his_time - 历史时间，以_隔开，存入毫秒数
     */
    public String getHisTime() {
        return hisTime;
    }

    /**
     * 设置历史时间，以_隔开，存入毫秒数
     *
     * @param hisTime 历史时间，以_隔开，存入毫秒数
     */
    public void setHisTime(String hisTime) {
        this.hisTime = hisTime;
    }

    /**
     * 获取最终操作时间
     *
     * @return modify_time - 最终操作时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置最终操作时间
     *
     * @param modifyTime 最终操作时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}