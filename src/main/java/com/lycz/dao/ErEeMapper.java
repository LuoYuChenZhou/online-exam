package com.lycz.dao;

import com.lycz.model.ErEe;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ErEeMapper extends Mapper<ErEe> {

    List<Map<String, Object>> getInvitedList(@Param("userId") String userId);

    List<Map<String, Object>> getApplyList(@Param("userId") String userId);

    List<Map<String, Object>> getOtherErListByEe(@Param("eeId") String eeId, @Param("searchErName") String searchErName);

    List<Map<String, Object>> getErListByEe(@Param("eeId") String eeId);

    List<Map<String, Object>> getExamineeNoRelation(@Param("searchString") String searchString,
                                                    @Param("userId") String userId);

    List<String> getErIdListByEeId(@Param("eeId") String eeId);
}