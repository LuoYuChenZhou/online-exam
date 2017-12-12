package com.lycz.dao;

import com.lycz.model.EssayQuestions;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface EssayQuestionsMapper extends Mapper<EssayQuestions> {
}