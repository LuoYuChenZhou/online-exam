package com.lycz.dao;

import com.lycz.model.Grade;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface GradeMapper extends Mapper<Grade> {
}