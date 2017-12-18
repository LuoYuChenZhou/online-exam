package com.lycz.service.user;

import com.lycz.model.Examiner;
import com.lycz.service.base.IBaseServiceTk;

public interface ExaminerService extends IBaseServiceTk<Examiner> {

    Examiner erLogin(String userName, String password);
}
