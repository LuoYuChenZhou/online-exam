package com.lycz.service.base;

import com.lycz.controller.common.FixPageInfo;
import com.lycz.model.SysLog;

import java.util.Date;
import java.util.Map;

public interface SysLogService extends IBaseServiceTk<SysLog> {

    /**
     * 根据搜索条件查询日志列表
     */
    FixPageInfo<Map<String, Object>> getLogListBySearch(Date searchStartTime, Date searchEndTime, String searchLevel, String searchTitle, Integer page, Integer limit);
}
