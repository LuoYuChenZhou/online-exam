package com.lycz.service.paper.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.dao.PapersMapper;
import com.lycz.dao.ScoreMapper;
import com.lycz.model.Score;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.paper.PaperQuestionService;
import com.lycz.service.paper.PapersService;
import com.lycz.service.paper.ScoreService;
import com.lycz.service.questions.BaseQuestionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ScoreServiceImpl extends BaseServiceTk<Score> implements ScoreService {
    @Resource
    private ScoreMapper scoreMapper;


    @Override
    public Map<String, Object> getStartAndAllTime(String paperId, String eeId) {
        return scoreMapper.getStartAndAllTime(paperId, eeId);
    }
}
