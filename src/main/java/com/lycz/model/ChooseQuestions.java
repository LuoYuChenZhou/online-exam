package com.lycz.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "choose_questions")
public class ChooseQuestions {
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

    /**
     * 是否多选
     */
    @Column(name = "is_multi")
    private String isMulti;

    /**
     * 多选得分模式（1-严格，2-递进，3-指定，4-宽松）
严格：全对才得分
递进：每对一个加指定分数
指定：答对一个以上得指定分数
宽松：答对一个得全分
     */
    @Column(name = "score_type")
    private String scoreType;

    /**
     * 指定分数，配合得分模式的递进和指定使用
     */
    @Column(name = "assign_score")
    private Integer assignScore;

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
严格：全对才得分
递进：每对一个加指定分数
指定：答对一个以上得指定分数
宽松：答对一个得全分
     *
     * @return score_type - 多选得分模式（1-严格，2-递进，3-指定，4-宽松）
严格：全对才得分
递进：每对一个加指定分数
指定：答对一个以上得指定分数
宽松：答对一个得全分
     */
    public String getScoreType() {
        return scoreType;
    }

    /**
     * 设置多选得分模式（1-严格，2-递进，3-指定，4-宽松）
严格：全对才得分
递进：每对一个加指定分数
指定：答对一个以上得指定分数
宽松：答对一个得全分
     *
     * @param scoreType 多选得分模式（1-严格，2-递进，3-指定，4-宽松）
严格：全对才得分
递进：每对一个加指定分数
指定：答对一个以上得指定分数
宽松：答对一个得全分
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