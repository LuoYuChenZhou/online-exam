package com.lycz.model;

import java.util.Date;
import javax.persistence.*;

public class Score {
    @Id
    private String id;

    /**
     * 试卷id
     */
    @Column(name = "paper_id")
    private String paperId;

    /**
     * 考生id
     */
    @Column(name = "ee_id")
    private String eeId;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 答案，以json格式存入
     */
    private String answer;

    /**
     * 交卷时间
     */
    @Column(name = "commit_time")
    private Date commitTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
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
     * 获取考生id
     *
     * @return ee_id - 考生id
     */
    public String getEeId() {
        return eeId;
    }

    /**
     * 设置考生id
     *
     * @param eeId 考生id
     */
    public void setEeId(String eeId) {
        this.eeId = eeId;
    }

    /**
     * 获取分数
     *
     * @return score - 分数
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置分数
     *
     * @param score 分数
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取答案，以json格式存入
     *
     * @return answer - 答案，以json格式存入
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置答案，以json格式存入
     *
     * @param answer 答案，以json格式存入
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 获取交卷时间
     *
     * @return commit_time - 交卷时间
     */
    public Date getCommitTime() {
        return commitTime;
    }

    /**
     * 设置交卷时间
     *
     * @param commitTime 交卷时间
     */
    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }
}