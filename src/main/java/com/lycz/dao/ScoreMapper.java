package com.lycz.dao;

import com.lycz.model.Score;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface ScoreMapper extends Mapper<Score> {

    Map<String, Object> getStartAndAllTime(@Param("paperId") String paperId, @Param("eeId") String eeId);
}