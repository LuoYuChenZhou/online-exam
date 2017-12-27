package com.lycz.model;

import javax.persistence.*;

@Table(name = "ee_grade")
public class EeGrade {
    /**
     * 主键id
     */
    @Id
    private String id;

    /**
     * 考生id
     */
    @Column(name = "ee_id")
    private String eeId;

    /**
     * 班级id
     */
    @Column(name = "grade_id")
    private String gradeId;

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
     * 获取考生id
     *
     * @return ee_id - 考生id
     */
    public String getEeId() {
        return eeId;
    }

    /**
     * 设置考生id
     *
     * @param eeId 考生id
     */
    public void setEeId(String eeId) {
        this.eeId = eeId;
    }

    /**
     * 获取班级id
     *
     * @return grade_id - 班级id
     */
    public String getGradeId() {
        return gradeId;
    }

    /**
     * 设置班级id
     *
     * @param gradeId 班级id
     */
    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }
}