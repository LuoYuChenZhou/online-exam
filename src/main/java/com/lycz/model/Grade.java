package com.lycz.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "grade")
public class Grade {
    /**
     * 班级id
     */
    @Id
    private String id;

    /**
     * 所属考官id
     */
    @Column(name = "er_id")
    private String erId;

    /**
     * 班级名称
     */
    @Column(name = "grade_name")
    private String gradeName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 排序号，越大排越前
     */
    @Column(name = "sort_no")
    private Byte sortNo;

    /**
     * 状态（0-禁用，1-正常，4-删除）
     */
    private String status;

    /**
     * 获取班级id
     *
     * @return id - 班级id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置班级id
     *
     * @param id 班级id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取所属考官id
     *
     * @return er_id - 所属考官id
     */
    public String getErId() {
        return erId;
    }

    /**
     * 设置所属考官id
     *
     * @param erId 所属考官id
     */
    public void setErId(String erId) {
        this.erId = erId;
    }

    /**
     * 获取班级名称
     *
     * @return grade_name - 班级名称
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * 设置班级名称
     *
     * @param gradeName 班级名称
     */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取排序号，越大排越前
     *
     * @return sort_no - 排序号，越大排越前
     */
    public Byte getSortNo() {
        return sortNo;
    }

    /**
     * 设置排序号，越大排越前
     *
     * @param sortNo 排序号，越大排越前
     */
    public void setSortNo(Byte sortNo) {
        this.sortNo = sortNo;
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