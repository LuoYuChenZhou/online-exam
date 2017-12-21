package com.lycz.service.base.impl;

import com.lycz.model.SysLog;
import com.lycz.service.base.SysLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysLogServiceImpl extends BaseServiceTk<SysLog> implements SysLogService {

}
