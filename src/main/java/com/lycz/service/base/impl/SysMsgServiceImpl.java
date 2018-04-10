package com.lycz.service.base.impl;

import com.lycz.model.SysMsg;
import com.lycz.service.base.SysMsgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysMsgServiceImpl extends BaseServiceTk<SysMsg> implements SysMsgService {

}
