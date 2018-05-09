package com.lycz.service.paper;

import com.lycz.model.PaperQuestion;
import com.lycz.service.base.IBaseServiceTk;

import java.util.List;
import java.util.Map;

public interface PaperQuestionService extends IBaseServiceTk<PaperQuestion> {

    int batchInsertPQ(List<PaperQuestion> pqList);

    int batchModifyPQ(List<PaperQuestion> pqList);

    int batchDelPQ(String paperId, List<String> qaIdList);

    /**
     * 只要传入了notShowAnswer，就不查询答案字段
     */
    List<Map<String, Object>> getPaperQuestionInfoById(String notShowAnswer, String paperId);

    /**
     * 只要传入了notShowAnswer，就不查询答案字段,相比于上面那个，这个加入了“保存Score对象”的操作
     */
    List<Map<String, Object>> getPaperQuestionInfoById(String notShowAnswer, String paperId, String eeId);
}
