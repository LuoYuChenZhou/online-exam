package com.lycz.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CommonMapper {
    void setSortNoByLineNum(@Param("tableName") String tableName,
                            @Param("operateId") String operateId,
                            @Param("sortNo") int sortNo,
                            @Param("orderTime") int orderTime,
                            @Param("otherWhere") String otherWhere);
}