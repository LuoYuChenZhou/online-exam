package com.lycz.dao;

import com.lycz.model.Examinee;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ExamineeMapper extends Mapper<Examinee> {
}