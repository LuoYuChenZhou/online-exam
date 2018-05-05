package com.lycz.service.questions.impl;

import com.lycz.dao.BaseQuestionsMapper;
import com.lycz.model.BaseQuestions;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.questions.BaseQuestionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class BaseQuestionsServiceImpl extends BaseServiceTk<BaseQuestions> implements BaseQuestionsService {

    @Resource
    private BaseQuestionsMapper baseQuestionsMapper;

    @Override
    public int batchInsertBQ(List<BaseQuestions> bqList) {
        return baseQuestionsMapper.batchInsertBQ(bqList);
    }
}
