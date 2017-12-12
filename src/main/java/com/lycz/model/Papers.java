package com.lycz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Papers {
    /**
     * 试卷id
     */
    @Id
    private String id;

    /**
     * 试卷名称
     */
    @Column(name = "papers_name")
    private String papersName;

    /**
     * 考试时间（以分钟为单位）
     */
    @Column(name = "exam_time")
    private Integer examTime;

    /**
     * 创建者
     */
    @Column(name = "examiner_id")
    private String examinerId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取试卷id
     *
     * @return id - 试卷id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置试卷id
     *
     * @param id 试卷id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取试卷名称
     *
     * @return papers_name - 试卷名称
     */
    public String getPapersName() {
        return papersName;
    }

    /**
     * 设置试卷名称
     *
     * @param papersName 试卷名称
     */
    public void setPapersName(String papersName) {
        this.papersName = papersName;
    }

    /**
     * 获取考试时间（以分钟为单位）
     *
     * @return exam_time - 考试时间（以分钟为单位）
     */
    public Integer getExamTime() {
        return examTime;
    }

    /**
     * 设置考试时间（以分钟为单位）
     *
     * @param examTime 考试时间（以分钟为单位）
     */
    public void setExamTime(Integer examTime) {
        this.examTime = examTime;
    }

    /**
     * 获取创建者
     *
     * @return examiner_id - 创建者
     */
    public String getExaminerId() {
        return examinerId;
    }

    /**
     * 设置创建者
     *
     * @param examinerId 创建者
     */
    public void setExaminerId(String examinerId) {
        this.examinerId = examinerId;
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
}