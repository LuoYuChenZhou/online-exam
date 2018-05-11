package com.lycz.service.paper.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.dao.PapersMapper;
import com.lycz.model.BaseQuestions;
import com.lycz.model.PaperQuestion;
import com.lycz.model.Papers;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.grade.EeGradeService;
import com.lycz.service.paper.PaperQuestionService;
import com.lycz.service.paper.PapersService;
import com.lycz.service.questions.BaseQuestionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PapersServiceImpl extends BaseServiceTk<Papers> implements PapersService {
    @Resource
    private PapersMapper papersMapper;
    @Resource
    private BaseQuestionsService baseQuestionsService;
    @Resource
    private PaperQuestionService paperQuestionService;
    @Resource
    private EeGradeService eeGradeService;

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
    public FixPageInfo<Map<String, Object>> selectPapersByErName(String eeId, String papersName, String teachersId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> tempList = papersMapper.selectPapersByErName(eeId, papersName, teachersId);

        if (ToolUtil.isEmpty(tempList)) {
            return null;
        }

        // 查询考生所在班级
        List<String> eeGradeList = eeGradeService.getGradeListByErEe(teachersId, eeId);

        // 限定班级条件
        List<Map<String, Object>> fixPaperList = new ArrayList<>();
        for (Map<String, Object> paperInfo : tempList) {
            if (ToolUtil.isNotEmpty(paperInfo.get("allowGrade")) && !Objects.equals(paperInfo.get("allowGrade"), "allGrade")) {
                String allGradeStr = (String) paperInfo.get("allowGrade");
                String[] allowGradeArray = allGradeStr.substring(0, allGradeStr.length() - 1).split(",");
                for (String s : allowGradeArray) {
                    if (eeGradeList.indexOf(s) >= 0) {
                        fixPaperList.add(paperInfo);
                        break;
                    }
                }
            } else {
                fixPaperList.add(paperInfo);
            }
        }

        if (ToolUtil.isEmpty(fixPaperList)) {
            return null;
        }
        return new FixPageInfo<>(fixPaperList);
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

    @Override
    public boolean modifyPaper(Papers paperInfo, List<PaperQuestion> pqAddList, List<PaperQuestion> pqModifyList, List<BaseQuestions> bqAddList, List<BaseQuestions> bqModifyList, String delQaId) {
        if (ToolUtil.isNotEmpty(bqAddList)) {
            if (baseQuestionsService.batchInsertBQ(bqAddList) < 1
                    || paperQuestionService.batchInsertPQ(pqAddList) < 1) {
                return false;
            }
        }
        if (ToolUtil.isNotEmpty(bqModifyList)) {
            if (baseQuestionsService.batchModifyBQ(bqModifyList) < 1
                    || paperQuestionService.batchModifyPQ(pqModifyList) < 1) {
                return false;
            }
        }
        if (ToolUtil.isNotEmpty(delQaId)) {
            List<String> qaIdList = new ArrayList<>();
            for (String s : delQaId.split(",")) {
                if (ToolUtil.isNotEmpty(s)) {
                    qaIdList.add(s);
                }
            }
            if (ToolUtil.isNotEmpty(qaIdList)) {
                if (baseQuestionsService.batchDelNotBankQuestion(qaIdList) < 1
                        || paperQuestionService.batchDelPQ(paperInfo.getId(), qaIdList) < 1) {
                    return false;
                }
            }
        }
        return updateByPrimaryKeySelective(paperInfo) > 0;
    }


}
