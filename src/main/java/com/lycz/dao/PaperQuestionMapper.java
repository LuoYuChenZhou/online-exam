package com.lycz.dao;

import com.lycz.model.PaperQuestion;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface PaperQuestionMapper extends Mapper<PaperQuestion> {
}