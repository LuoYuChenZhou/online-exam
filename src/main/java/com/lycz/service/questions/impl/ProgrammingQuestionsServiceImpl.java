package com.lycz.service.questions.impl;

import com.lycz.model.ProgrammingQuestions;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.questions.ProgrammingQuestionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProgrammingQuestionsServiceImpl extends BaseServiceTk<ProgrammingQuestions> implements ProgrammingQuestionsService {
}
