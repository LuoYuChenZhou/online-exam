package com.lycz.dao;

import com.lycz.model.Papers;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface PapersMapper extends Mapper<Papers> {
    List<Map<String, Object>> selectPapersByName(@Param("papersName") String papersName,
                                                    @Param("teachersId") String teachersId);
}