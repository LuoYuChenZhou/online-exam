package com.lycz.service.user.impl;

import com.lycz.model.Examinee;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.user.ExamineeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExamineeServiceImpl extends BaseServiceTk<Examinee> implements ExamineeService {
}
