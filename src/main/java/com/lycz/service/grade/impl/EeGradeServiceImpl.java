package com.lycz.service.grade.impl;

import com.lycz.dao.EeGradeMapper;
import com.lycz.model.EeGrade;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.grade.EeGradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EeGradeServiceImpl extends BaseServiceTk<EeGrade> implements EeGradeService {
    @Resource
    private EeGradeMapper eeGradeMapper;

    @Override
    public Integer insertEeToGrade(String eeId, String gradeId) {

        return eeGradeMapper.insertEeToGrade(eeId, gradeId);
    }
}
