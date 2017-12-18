package com.lycz.service.paper.impl;

import com.lycz.model.PaperQuestion;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.paper.PaperQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PaperQuestionServiceImpl extends BaseServiceTk<PaperQuestion> implements PaperQuestionService {
}
