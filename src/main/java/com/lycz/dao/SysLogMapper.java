package com.lycz.dao;

import com.lycz.model.SysLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface SysLogMapper extends Mapper<SysLog> {

    List<Map<String, Object>> getLogListBySearch(@Param("searchStartTime") Date searchStartTime,
                                                 @Param("searchEndTime") Date searchEndTime,
                                                 @Param("searchLevel") String searchLevel,
                                                 @Param("searchTitle") String searchTitle);
}