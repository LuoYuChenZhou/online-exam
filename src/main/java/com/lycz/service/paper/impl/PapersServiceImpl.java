package com.lycz.service.paper.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.dao.PapersMapper;
import com.lycz.model.BaseQuestions;
import com.lycz.model.PaperQuestion;
import com.lycz.model.Papers;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.paper.PaperQuestionService;
import com.lycz.service.paper.PapersService;
import com.lycz.service.questions.BaseQuestionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PapersServiceImpl extends BaseServiceTk<Papers> implements PapersService {
    @Resource
    private PapersMapper papersMapper;
    @Resource
    private BaseQuestionsService baseQuestionsService;
    @Resource
    private PaperQuestionService paperQuestionService;

    @Override
    public FixPageInfo<Map<String, Object>> selectPapersByName(String papersName, String teachersId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> tempList = papersMapper.selectPapersByName(papersName, teachersId);
        if (ToolUtil.isEmpty(tempList)) {
            return null;
        }
        return new FixPageInfo<>(tempList);
    }

    @Override
    public boolean addNewPaper(Papers paperInfo, List<BaseQuestions> baseQuestionsList, List<PaperQuestion> paperQuestionList) {
        if (ToolUtil.isNotEmpty(baseQuestionsList)) {
            if (baseQuestionsService.batchInsertBQ(baseQuestionsList) < 1
                    || paperQuestionService.batchInsertPQ(paperQuestionList) < 1) {
                return false;
            }
        }
        return insertSelective(paperInfo) > 0;
    }

}
