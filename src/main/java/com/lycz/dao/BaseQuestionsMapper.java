package com.lycz.dao;

import com.lycz.model.BaseQuestions;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BaseQuestionsMapper extends Mapper<BaseQuestions> {
    int batchInsertBQ(@Param("bqList") List<BaseQuestions> bqList);
}