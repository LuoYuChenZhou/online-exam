package com.lycz.service.questions.impl;

import com.lycz.model.BaseQuestions;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.questions.BaseQuestionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BaseQuestionsServiceImpl extends BaseServiceTk<BaseQuestions> implements BaseQuestionsService {
}
