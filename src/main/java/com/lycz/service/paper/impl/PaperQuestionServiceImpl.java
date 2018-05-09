package com.lycz.service.paper.impl;

import com.lycz.dao.PaperQuestionMapper;
import com.lycz.model.PaperQuestion;
import com.lycz.model.Score;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.paper.PaperQuestionService;
import com.lycz.service.paper.ScoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PaperQuestionServiceImpl extends BaseServiceTk<PaperQuestion> implements PaperQuestionService {

    @Resource
    private PaperQuestionMapper paperQuestionMapper;
    @Resource
    private ScoreService scoreService;

    @Override
    public int batchInsertPQ(List<PaperQuestion> pqList) {
        return paperQuestionMapper.batchInsertPQ(pqList);
    }

    @Override
    public int batchModifyPQ(List<PaperQuestion> pqList) {
        return paperQuestionMapper.batchModifyPQ(pqList);
    }

    @Override
    public int batchDelPQ(String paperId, List<String> qaIdList) {
        return paperQuestionMapper.batchDelPQ(paperId, qaIdList);
    }

    @Override
    public List<Map<String, Object>> getPaperQuestionInfoById(String notShowAnswer, String paperId) {
        return paperQuestionMapper.getPaperQuestionInfoById(notShowAnswer, paperId);
    }

    @Override
    public List<Map<String, Object>> getPaperQuestionInfoById(String notShowAnswer, String paperId, String eeId) {
        Score score = new Score();
        score.setId(UUID.randomUUID().toString());
        score.setBlurStartTime(new Date());
        score.setPaperId(paperId);
        score.setEeId(eeId);
        if (scoreService.insertSelective(score) < 0) {
            return null;
        }
        return paperQuestionMapper.getPaperQuestionInfoById(notShowAnswer, paperId);
    }
}
