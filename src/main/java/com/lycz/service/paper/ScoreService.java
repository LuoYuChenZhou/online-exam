package com.lycz.service.paper;

import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.model.Score;
import com.lycz.service.base.IBaseServiceTk;

import java.util.List;
import java.util.Map;

public interface ScoreService extends IBaseServiceTk<Score> {

    /**
     * 根据试卷id和考生id获取试卷的考试时间和考试的大概开始时间
     */
    Map<String, Object> getStartAndAllTime(String paperId, String eeId);

    FixPageInfo<Map<String, Object>> getScoreListByPaperId(String paperId, String searchEeInfo, Integer page, Integer limit);

    int deleteByPaperId(String paperId);
}
