package com.lycz.dao;

import com.lycz.model.PaperQuestion;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface PaperQuestionMapper extends Mapper<PaperQuestion> {
    int batchInsertPQ(@Param("pqList") List<PaperQuestion> pqList);

    int batchModifyPQ(@Param("pqList") List<PaperQuestion> pqList);

    int batchDelPQ(@Param("paperId") String paperId, @Param("qaIdList") List<String> qaIdList);

    List<Map<String, Object>> getPaperQuestionInfoById(@Param("notShowAnswer") String notShowAnswer, @Param("paperId") String paperId);
}