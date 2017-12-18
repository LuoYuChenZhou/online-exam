package com.lycz.service.grade.impl;

import com.lycz.model.EeGrade;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.grade.EeGradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EeGradeServiceImpl extends BaseServiceTk<EeGrade> implements EeGradeService {
}
