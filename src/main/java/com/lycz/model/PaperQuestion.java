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
     * 问题类型（1-选择，2-填空，3-简答，4-编程）
     */
    @Column(name = "question_type")
    private String questionType;

    /**
     * 得分模式，详见文档
     */
    @Column(name = "score_type")
    private String scoreType;

    /**
     * 分数
     */
    @Column(name = "question_score")
    private String questionScore;

    /**
     * 总数
     */
    @Column(name = "full_score")
    private String fullScore;

    /**
     * 指定分数，配合得分模式的递进和指定使用
     */
    @Column(name = "assign_score")
    private Integer assignScore;

    /**
     * 批改模式（0-手动批改，1-自动批改）
     */
    @Column(name = "correct_type")
    private String correctType;

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

    /**
     * 获取得分模式，详见文档
     *
     * @return score_type - 得分模式，详见文档
     */
    public String getScoreType() {
        return scoreType;
    }

    /**
     * 设置得分模式，详见文档
     *
     * @param scoreType 得分模式，详见文档
     */
    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(String questionScore) {
        this.questionScore = questionScore;
    }

    public String getFullScore() {
        return fullScore;
    }

    public void setFullScore(String fullScore) {
        this.fullScore = fullScore;
    }

    /**
     * 获取指定分数，配合得分模式的递进和指定使用
     *
     * @return assign_score - 指定分数，配合得分模式的递进和指定使用
     */
    public Integer getAssignScore() {
        return assignScore;
    }

    /**
     * 设置指定分数，配合得分模式的递进和指定使用
     *
     * @param assignScore 指定分数，配合得分模式的递进和指定使用
     */
    public void setAssignScore(Integer assignScore) {
        this.assignScore = assignScore;
    }

    public String getCorrectType() {
        return correctType;
    }

    public void setCorrectType(String correctType) {
        this.correctType = correctType;
    }
}