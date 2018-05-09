package com.lycz.service.paper;

import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.model.BaseQuestions;
import com.lycz.model.PaperQuestion;
import com.lycz.model.Papers;
import com.lycz.service.base.IBaseServiceTk;

import java.util.List;
import java.util.Map;

public interface PapersService extends IBaseServiceTk<Papers> {

    FixPageInfo<Map<String, Object>> selectPapersByName(String papersName,
                                                        String teachersId,
                                                        Integer page,
                                                        Integer limit);

    FixPageInfo<Map<String, Object>> selectPapersByErName(String eeId,
                                                          String papersName,
                                                          String teachersId,
                                                          Integer page,
                                                          Integer limit);

    boolean addNewPaper(Papers paperInfo, List<BaseQuestions> baseQuestionsList, List<PaperQuestion> paperQuestionList);

    boolean modifyPaper(Papers paperInfo,
                        List<PaperQuestion> pqAddList,
                        List<PaperQuestion> pqModifyList,
                        List<BaseQuestions> bqAddList,
                        List<BaseQuestions> bqModifyList,
                        String delQaId);

}
