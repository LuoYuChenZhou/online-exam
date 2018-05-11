package com.lycz.dao;

import com.lycz.model.Score;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ScoreMapper extends Mapper<Score> {

    Map<String, Object> getStartAndAllTime(@Param("paperId") String paperId, @Param("eeId") String eeId);

    List<Map<String, Object>> getScoreListByPaperId(@Param("paperId") String paperId, @Param("searchEeInfo") String searchEeInfo);

    int deleteByPaperId(@Param("paperId") String paperId);
}