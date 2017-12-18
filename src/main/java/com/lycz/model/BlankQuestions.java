package com.lycz.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "blank_questions")
public class BlankQuestions {
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
     * 分数,与空数对应，逗号分开
     */
    @Column(name = "question_score")
    private String questionScore;

    /**
     * 批改模式（1-自动批改，2-手动批改）
     */
    @Column(name = "correct_type")
    private String correctType;

    /**
     * 自动批改得分模式（1-严格，2-包含，3-严格包含）
严格：文字与某一个答案完全相同才得分
包含：包含任何答案文字得分
严格包含：包含但不完全相同得指定分

     */
    @Column(name = "score_type")
    private String scoreType;

    /**
     * 指定分数，配合得分模式的严格包含使用
     */
    @Column(name = "assign_score")
    private Integer assignScore;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "create_time")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 状态（0-禁用，1-正常，4-删除）
     */
    private String status;

    /**
     * 问题描述（空处用$$表示）
     */
    @Column(name = "question_desc")
    private String questionDesc;

    /**
     * 答案（同一空的不同答案用<,>隔开，不同的用<;>隔开）
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
     * 获取分数,与空数对应，逗号分开
     *
     * @return question_score - 分数,与空数对应，逗号分开
     */
    public String getQuestionScore() {
        return questionScore;
    }

    /**
     * 设置分数,与空数对应，逗号分开
     *
     * @param questionScore 分数,与空数对应，逗号分开
     */
    public void setQuestionScore(String questionScore) {
        this.questionScore = questionScore;
    }

    /**
     * 获取批改模式（1-自动批改，2-手动批改）
     *
     * @return correct_type - 批改模式（1-自动批改，2-手动批改）
     */
    public String getCorrectType() {
        return correctType;
    }

    /**
     * 设置批改模式（1-自动批改，2-手动批改）
     *
     * @param correctType 批改模式（1-自动批改，2-手动批改）
     */
    public void setCorrectType(String correctType) {
        this.correctType = correctType;
    }

    /**
     * 获取自动批改得分模式（1-严格，2-包含，3-严格包含）
严格：文字与某一个答案完全相同才得分
包含：包含任何答案文字得分
严格包含：包含但不完全相同得指定分

     *
     * @return score_type - 自动批改得分模式（1-严格，2-包含，3-严格包含）
严格：文字与某一个答案完全相同才得分
包含：包含任何答案文字得分
严格包含：包含但不完全相同得指定分

     */
    public String getScoreType() {
        return scoreType;
    }

    /**
     * 设置自动批改得分模式（1-严格，2-包含，3-严格包含）
严格：文字与某一个答案完全相同才得分
包含：包含任何答案文字得分
严格包含：包含但不完全相同得指定分

     *
     * @param scoreType 自动批改得分模式（1-严格，2-包含，3-严格包含）
严格：文字与某一个答案完全相同才得分
包含：包含任何答案文字得分
严格包含：包含但不完全相同得指定分

     */
    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    /**
     * 获取指定分数，配合得分模式的严格包含使用
     *
     * @return assign_score - 指定分数，配合得分模式的严格包含使用
     */
    public Integer getAssignScore() {
        return assignScore;
    }

    /**
     * 设置指定分数，配合得分模式的严格包含使用
     *
     * @param assignScore 指定分数，配合得分模式的严格包含使用
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
     * 获取问题描述（空处用$$表示）
     *
     * @return question_desc - 问题描述（空处用$$表示）
     */
    public String getQuestionDesc() {
        return questionDesc;
    }

    /**
     * 设置问题描述（空处用$$表示）
     *
     * @param questionDesc 问题描述（空处用$$表示）
     */
    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    /**
     * 获取答案（同一空的不同答案用<,>隔开，不同的用<;>隔开）
     *
     * @return answer - 答案（同一空的不同答案用<,>隔开，不同的用<;>隔开）
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置答案（同一空的不同答案用<,>隔开，不同的用<;>隔开）
     *
     * @param answer 答案（同一空的不同答案用<,>隔开，不同的用<;>隔开）
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}