package com.lycz.service.user;

import com.lycz.model.Examinee;
import com.lycz.service.base.IBaseServiceTk;

public interface ExamineeService extends IBaseServiceTk<Examinee> {

    /**
     * 考生登录
     */
    Examinee eeLogin(String userName, String password);

    /**
     * 验证用户名是否已被占用
     */
    boolean userNameIsExist(String userName);
}
