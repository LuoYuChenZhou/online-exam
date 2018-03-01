package com.lycz.dao;

import com.lycz.model.Grade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface GradeMapper extends Mapper<Grade> {
    List<Map<String, Object>> getGradeListByNameUser(@Param("searchGradeName") String searchGradeName,
                                                     @Param("userId") String userId);

}