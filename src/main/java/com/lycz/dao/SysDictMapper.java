package com.lycz.dao;

import com.lycz.model.SysDict;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysDictMapper extends Mapper<SysDict> {

    List<Map<String, Object>> getDictListByNameValueCodeUpperId(@Param("searchString") String searchString,
                                                                @Param("upperId") String upperId);
}