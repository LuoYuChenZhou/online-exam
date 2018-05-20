package com.lycz.dao;

import com.lycz.model.SysMsg;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysMsgMapper extends Mapper<SysMsg> {
    List<Map<String, Object>> getMsgListByTimeUser(@Param("searchTime") String searchTime, @Param("userId") String userId);

    int batchInsertMsg(@Param("msgList") List<SysMsg> msgList);
}