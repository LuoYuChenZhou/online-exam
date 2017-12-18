package com.lycz.model;

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
}