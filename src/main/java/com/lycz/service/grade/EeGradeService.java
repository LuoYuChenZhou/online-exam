package com.lycz.service.grade;

import com.lycz.model.EeGrade;
import com.lycz.service.base.IBaseServiceTk;

public interface EeGradeService extends IBaseServiceTk<EeGrade> {

    Integer insertGrade(String eeId, String gradeId);
}
