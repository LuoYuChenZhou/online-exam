package com.lycz.service.paper;

import com.lycz.model.PaperQuestion;
import com.lycz.service.base.IBaseServiceTk;

import java.util.List;
import java.util.Map;

public interface PaperQuestionService extends IBaseServiceTk<PaperQuestion> {

    int batchInsertPQ(List<PaperQuestion> pqList);

    List<Map<String, Object>> getPaperQuestionInfoById(String paperId);
}
