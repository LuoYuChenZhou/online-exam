package com.lycz.model;

import javax.persistence.*;

@Table(name = "paper_question")
public class PaperQuestion {
    /**
     * 主键id
     */
    @Id
    private String id;

    /**
     * 试卷id
     */
    @Column(name = "paper_id")
    private String paperId;

    /**
     * 问题id
     */
    @Column(name = "question_id")
    private String questionId;

    /**
     * 题号，题目的展示顺序
     */
    @Column(name = "question_no")
    private Byte questionNo;

    /**
     * 问题类型（1-选择，2-填空，3-简答，4-编程）
     */
    @Column(name = "question_type")
    private String questionType;

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
     * 获取试卷id
     *
     * @return paper_id - 试卷id
     */
    public String getPaperId() {
        return paperId;
    }

    /**
     * 设置试卷id
     *
     * @param paperId 试卷id
     */
    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    /**
     * 获取问题id
     *
     * @return question_id - 问题id
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * 设置问题id
     *
     * @param questionId 问题id
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    /**
     * 获取题号，题目的展示顺序
     *
     * @return question_no - 题号，题目的展示顺序
     */
    public Byte getQuestionNo() {
        return questionNo;
    }

    /**
     * 设置题号，题目的展示顺序
     *
     * @param questionNo 题号，题目的展示顺序
     */
    public void setQuestionNo(Byte questionNo) {
        this.questionNo = questionNo;
    }

    /**
     * 获取问题类型（1-选择，2-填空，3-简答，4-编程）
     *
     * @return question_type - 问题类型（1-选择，2-填空，3-简答，4-编程）
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * 设置问题类型（1-选择，2-填空，3-简答，4-编程）
     *
     * @param questionType 问题类型（1-选择，2-填空，3-简答，4-编程）
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
}