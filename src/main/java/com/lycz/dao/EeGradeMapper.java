package com.lycz.dao;

import com.lycz.model.EeGrade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface EeGradeMapper extends Mapper<EeGrade> {

    List<Map<String, Object>> getEeListByNameNoClass(@Param("erId") String erId,
                                                     @Param("searchClass") String searchClass,
                                                     @Param("searchString") String searchString);

    List<Map<String, Object>> getNoEeGradeList(@Param("erId") String erId, @Param("eeId") String eeId);

    Integer deleteByEeEr(@Param("erId") String erId, @Param("eeId") String eeId);

}