package com.lycz.service.questions.impl;

import com.lycz.model.EssayQuestions;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.questions.EssayQuestionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EssayQusetionsServiceImpl extends BaseServiceTk<EssayQuestions> implements EssayQuestionsService {
}
