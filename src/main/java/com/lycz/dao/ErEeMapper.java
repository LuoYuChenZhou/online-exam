package com.lycz.dao;

import com.lycz.model.ErEe;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ErEeMapper extends Mapper<ErEe> {

    List<Map<String, Object>> getExamineeNoRelation(@Param("searchEeName") String searchEeName,
                                                    @Param("searchEeNo") String searchEeNo,
                                                    @Param("userId") String userId);
}