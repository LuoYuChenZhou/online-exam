package com.lycz.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "programming_questions")
public class ProgrammingQuestions {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 所属考官id
     */
    @Column(name = "er_id")
    private String erId;

    /**
     * 所属试卷id
     */
    @Column(name = "paper_id")
    private String paperId;

    /**
     * 分数
     */
    @Column(name = "question_score")
    private Integer questionScore;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 状态（0-禁用，1-正常，4-删除）
     */
    private String status;

    /**
     * 问题描述
     */
    @Column(name = "question_desc")
    private String questionDesc;

    /**
     * 测试用例
     */
    @Column(name = "test_case")
    private String testCase;

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
     * 获取所属试卷id
     *
     * @return paper_id - 所属试卷id
     */
    public String getPaperId() {
        return paperId;
    }

    /**
     * 设置所属试卷id
     *
     * @param paperId 所属试卷id
     */
    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    /**
     * 获取分数
     *
     * @return question_score - 分数
     */
    public Integer getQuestionScore() {
        return questionScore;
    }

    /**
     * 设置分数
     *
     * @param questionScore 分数
     */
    public void setQuestionScore(Integer questionScore) {
        this.questionScore = questionScore;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
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

    /**
     * 获取问题描述
     *
     * @return question_desc - 问题描述
     */
    public String getQuestionDesc() {
        return questionDesc;
    }

    /**
     * 设置问题描述
     *
     * @param questionDesc 问题描述
     */
    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    /**
     * 获取测试用例
     *
     * @return test_case - 测试用例
     */
    public String getTestCase() {
        return testCase;
    }

    /**
     * 设置测试用例
     *
     * @param testCase 测试用例
     */
    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }
}