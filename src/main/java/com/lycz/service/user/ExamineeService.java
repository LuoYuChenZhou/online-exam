package com.lycz.service.user;

import com.lycz.model.Examinee;
import com.lycz.service.base.IBaseServiceTk;

public interface ExamineeService extends IBaseServiceTk<Examinee> {

    Examinee eeLogin(String userName, String password);

}
