package com.lycz.dao;

import com.lycz.model.EeGrade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface EeGradeMapper extends Mapper<EeGrade> {
}