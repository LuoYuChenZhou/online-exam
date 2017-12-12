package com.lycz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "programming_questions")
public class ProgrammingQuestions {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 所属试卷id
     */
    @Column(name = "paper_id")
    private String paperId;

    /**
     * 试题号
     */
    @Column(name = "qusetion_no")
    private Integer qusetionNo;

    /**
     * 分数
     */
    @Column(name = "question_score")
    private Integer questionScore;

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
     * 获取试题号
     *
     * @return qusetion_no - 试题号
     */
    public Integer getQusetionNo() {
        return qusetionNo;
    }

    /**
     * 设置试题号
     *
     * @param qusetionNo 试题号
     */
    public void setQusetionNo(Integer qusetionNo) {
        this.qusetionNo = qusetionNo;
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