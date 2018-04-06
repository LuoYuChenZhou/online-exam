package com.lycz.dao;

import com.lycz.model.Examinee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface ExamineeMapper extends Mapper<Examinee> {

}