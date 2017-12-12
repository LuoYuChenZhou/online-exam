package com.lycz.dao;

import com.lycz.model.Papers;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface PapersMapper extends Mapper<Papers> {
}