package com.lycz.service.user.impl;

import com.lycz.model.ErEe;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.user.ErEeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ErEeServiceImpl extends BaseServiceTk<ErEe> implements ErEeService {
}
