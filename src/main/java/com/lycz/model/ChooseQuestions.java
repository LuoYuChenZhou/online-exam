package com.lycz.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "choose_questions")
public class ChooseQuestions {
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
     * 是否多选
     */
    @Column(name = "is_multi")
    private String isMulti;

    /**
     * 多选得分模式（1-严格，2-递进，3-指定，4-宽松）
     * 严格：全对才得分
     * 递进：每对一个加指定分数
     * 指定：答对一个以上得指定分数
     * 宽松：答对一个得全分
     */
    @Column(name = "score_type")
    private String scoreType;

    /**
     * 指定分数，配合得分模式的递进和指定使用
     */
    @Column(name = "assign_score")
    private Integer assignScore;

    /**
     * 问题描述
     */
    @Column(name = "question_desc")
    private String questionDesc;

    /**
     * 答案（二进制存储）
     */
    private String answer;

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
     * 获取是否多选
     *
     * @return is_multi - 是否多选
     */
    public String getIsMulti() {
        return isMulti;
    }

    /**
     * 设置是否多选
     *
     * @param isMulti 是否多选
     */
    public void setIsMulti(String isMulti) {
        this.isMulti = isMulti;
    }

    /**
     * 获取多选得分模式（1-严格，2-递进，3-指定，4-宽松）
     * 严格：全对才得分
     * 递进：每对一个加指定分数
     * 指定：答对一个以上得指定分数
     * 宽松：答对一个得全分
     *
     * @return score_type - 多选得分模式（1-严格，2-递进，3-指定，4-宽松）
     * 严格：全对才得分
     * 递进：每对一个加指定分数
     * 指定：答对一个以上得指定分数
     * 宽松：答对一个得全分
     */
    public String getScoreType() {
        return scoreType;
    }

    /**
     * 设置多选得分模式（1-严格，2-递进，3-指定，4-宽松）
     * 严格：全对才得分
     * 递进：每对一个加指定分数
     * 指定：答对一个以上得指定分数
     * 宽松：答对一个得全分
     *
     * @param scoreType 多选得分模式（1-严格，2-递进，3-指定，4-宽松）
     *                  严格：全对才得分
     *                  递进：每对一个加指定分数
     *                  指定：答对一个以上得指定分数
     *                  宽松：答对一个得全分
     */
    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
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
     * 获取答案（二进制存储）
     *
     * @return answer - 答案（二进制存储）
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置答案（二进制存储）
     *
     * @param answer 答案（二进制存储）
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}