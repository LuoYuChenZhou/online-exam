package com.lycz.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.controller.common.FixPageInfo;
import com.lycz.controller.common.ToolUtil;
import com.lycz.dao.SysLogMapper;
import com.lycz.model.SysLog;
import com.lycz.service.base.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysLogServiceImpl extends BaseServiceTk<SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public FixPageInfo<Map<String, Object> > getLogListBySearch(Date searchStartTime, Date searchEndTime, String searchLevel, String searchTitle, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> list = sysLogMapper.getLogListBySearch(searchStartTime, searchEndTime, searchLevel, searchTitle);
        if(ToolUtil.isEmpty(list)){
            return  null;
        }else {
            return  new FixPageInfo<>(list);
        }
    }
}
