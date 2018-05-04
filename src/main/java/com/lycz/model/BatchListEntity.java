package com.lycz.model;

import java.util.List;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/5/4 16:40
 */
public class BatchListEntity {

    private List<BaseQuestions> baseQuestionsList;

    private List<PaperQuestion> paperQuestionList;

    public List<BaseQuestions> getBaseQuestionsList() {
        return baseQuestionsList;
    }

    public void setBaseQuestionsList(List<BaseQuestions> baseQuestionsList) {
        this.baseQuestionsList = baseQuestionsList;
    }

    public List<PaperQuestion> getPaperQuestionList() {
        return paperQuestionList;
    }

    public void setPaperQuestionList(List<PaperQuestion> paperQuestionList) {
        this.paperQuestionList = paperQuestionList;
    }
}
