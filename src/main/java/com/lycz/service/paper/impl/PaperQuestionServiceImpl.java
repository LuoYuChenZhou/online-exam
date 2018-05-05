package com.lycz.service.paper.impl;

import com.lycz.dao.PaperQuestionMapper;
import com.lycz.model.PaperQuestion;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.paper.PaperQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PaperQuestionServiceImpl extends BaseServiceTk<PaperQuestion> implements PaperQuestionService {

    @Resource
    private PaperQuestionMapper paperQuestionMapper;

    @Override
    public int batchInsertPQ(List<PaperQuestion> pqList) {
        return paperQuestionMapper.batchInsertPQ(pqList);
    }

    @Override
    public List<Map<String, Object>> getPaperQuestionInfoById(String paperId) {
        return paperQuestionMapper.getPaperQuestionInfoById(paperId);
    }
}
