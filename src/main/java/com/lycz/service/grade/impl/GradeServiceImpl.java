package com.lycz.service.grade.impl;

import com.lycz.model.Grade;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.grade.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GradeServiceImpl extends BaseServiceTk<Grade> implements GradeService {
}
