package com.lycz.dao;

import com.lycz.model.ProgrammingQuestions;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ProgrammingQuestionsMapper extends Mapper<ProgrammingQuestions> {
}