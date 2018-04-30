package com.lycz.model;

import java.util.Date;
import javax.persistence.*;

public class Papers {
    /**
     * 试卷id
     */
    @Id
    private String id;

    /**
     * 创建者
     */
    @Column(name = "examiner_id")
    private String examinerId;

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
     * 满分
     */
    @Column(name = "full_score")
    private Integer fullScore;

    /**
     * 默认批改模式（0-手动批改，1-自动批改）
     */
    @Column(name = "default_correct_type")
    private String defaultCorrectType;

    /**
     * 默认科目（存字典值）
     */
    @Column(name = "default_subject")
    private String defaultSubject;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 状态（1-编写，2-已发布，4-删除）
     */
    private String status;

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
     * 获取满分
     *
     * @return full_score - 满分
     */
    public Integer getFullScore() {
        return fullScore;
    }

    /**
     * 设置满分
     *
     * @param fullScore 满分
     */
    public void setFullScore(Integer fullScore) {
        this.fullScore = fullScore;
    }

    /**
     * 获取默认批改模式（0-手动批改，1-自动批改）
     *
     * @return default_correct_type - 默认批改模式（0-手动批改，1-自动批改）
     */
    public String getDefaultCorrectType() {
        return defaultCorrectType;
    }

    /**
     * 设置默认批改模式（0-手动批改，1-自动批改）
     *
     * @param defaultCorrectType 默认批改模式（0-手动批改，1-自动批改）
     */
    public void setDefaultCorrectType(String defaultCorrectType) {
        this.defaultCorrectType = defaultCorrectType;
    }

    /**
     * 获取默认科目（存字典值）
     *
     * @return default_subject - 默认科目（存字典值）
     */
    public String getDefaultSubject() {
        return defaultSubject;
    }

    /**
     * 设置默认科目（存字典值）
     *
     * @param defaultSubject 默认科目（存字典值）
     */
    public void setDefaultSubject(String defaultSubject) {
        this.defaultSubject = defaultSubject;
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
     * 获取状态（1-编写，2-已发布，4-删除）
     *
     * @return status - 状态（1-编写，2-已发布，4-删除）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态（1-编写，2-已发布，4-删除）
     *
     * @param status 状态（1-编写，2-已发布，4-删除）
     */
    public void setStatus(String status) {
        this.status = status;
    }
}