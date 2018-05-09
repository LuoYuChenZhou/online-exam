package com.lycz.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "base_questions")
public class BaseQuestions {
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
     * 问题类型（0-选择，1-填空，2-简答）
     */
    @Column(name = "question_type")
    private String questionType;

    /**
     * 是否多选（0-否，1-是），专用于选择题
     */
    @Column(name = "is_multi")
    private String isMulti;

    /**
     * 是否是题库问题（是则不能修改，0-否，1-是）
     */
    @Column(name = "is_bank")
    private String isBank;

    /**
     * 填空题[$$]中间的数字用逗号隔开组成的
     */
    @Column(name = "blank_index")
    private String blankIndex;

    /**
     * 科目（存字典id）
     */
    private String subject;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 状态（0-禁用，1-正常，4-删除）
     */
    private String status;

    /**
     * 问题描述，填空题填空处用$$表示
     */
    @Column(name = "question_desc")
    private String questionDesc;

    /**
     * 选项，以$$隔开，选择题专属
     */
    private String options;

    /**
     * 答案，存储规则详见文档
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
     * 获取问题类型（0-选择，1-填空，2-简答）
     *
     * @return question_type - 问题类型（0-选择，1-填空，2-简答）
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * 设置问题类型（0-选择，1-填空，2-简答）
     *
     * @param questionType 问题类型（0-选择，1-填空，2-简答）
     */
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    /**
     * 获取是否多选（0-否，1-是），专用于选择题
     *
     * @return is_multi - 是否多选（0-否，1-是），专用于选择题
     */
    public String getIsMulti() {
        return isMulti;
    }

    /**
     * 设置是否多选（0-否，1-是），专用于选择题
     *
     * @param isMulti 是否多选（0-否，1-是），专用于选择题
     */
    public void setIsMulti(String isMulti) {
        this.isMulti = isMulti;
    }

    public String getIsBank() {
        return isBank;
    }

    public void setIsBank(String isBank) {
        this.isBank = isBank;
    }

    public String getBlankIndex() {
        return blankIndex;
    }

    public void setBlankIndex(String blankIndex) {
        this.blankIndex = blankIndex;
    }

    /**
     * 获取科目（存字典id）
     *
     * @return subject - 科目（存字典id）
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 设置科目（存字典id）
     *
     * @param subject 科目（存字典id）
     */
    public void setSubject(String subject) {
        this.subject = subject;
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
     * 获取问题描述，填空题填空处用$$表示
     *
     * @return question_desc - 问题描述，填空题填空处用$$表示
     */
    public String getQuestionDesc() {
        return questionDesc;
    }

    /**
     * 设置问题描述，填空题填空处用$$表示
     *
     * @param questionDesc 问题描述，填空题填空处用$$表示
     */
    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    /**
     * 获取选项，以$$隔开，选择题专属
     *
     * @return options - 选项，以$$隔开，选择题专属
     */
    public String getOptions() {
        return options;
    }

    /**
     * 设置选项，以$$隔开，选择题专属
     *
     * @param options 选项，以$$隔开，选择题专属
     */
    public void setOptions(String options) {
        this.options = options;
    }

    /**
     * 获取答案，存储规则详见文档
     *
     * @return answer - 答案，存储规则详见文档
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置答案，存储规则详见文档
     *
     * @param answer 答案，存储规则详见文档
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}