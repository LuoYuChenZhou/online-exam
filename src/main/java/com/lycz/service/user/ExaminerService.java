package com.lycz.service.user;

import com.lycz.model.Examiner;
import com.lycz.service.base.IBaseServiceTk;

public interface ExaminerService extends IBaseServiceTk<Examiner> {

    /**
     * 考官登录
     */
    Examiner erLogin(String userName, String password);

    /**
     * 验证用户名是否已被占用
     */
    boolean userNameIsExist(String userName);
}
