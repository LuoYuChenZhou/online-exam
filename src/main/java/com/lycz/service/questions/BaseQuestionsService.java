package com.lycz.service.questions;

import com.lycz.model.BaseQuestions;
import com.lycz.service.base.IBaseServiceTk;

import java.util.List;

public interface BaseQuestionsService extends IBaseServiceTk<BaseQuestions> {

    int batchInsertBQ(List<BaseQuestions> bqList);
}
