package com.lycz.service.questions.impl;

import com.lycz.model.BlankQuestions;
import com.lycz.service.questions.BlankQuestionsService;
import com.lycz.service.base.impl.BaseServiceTk;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BlankQusetionsServiceImpl extends BaseServiceTk<BlankQuestions> implements BlankQuestionsService {
}
