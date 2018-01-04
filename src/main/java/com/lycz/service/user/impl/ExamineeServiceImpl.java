package com.lycz.service.user.impl;

import com.lycz.controller.common.ToolUtil;
import com.lycz.model.Examinee;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.user.ExamineeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExamineeServiceImpl extends BaseServiceTk<Examinee> implements ExamineeService {

    @Override
    public Examinee eeLogin(String userName, String password) {
        Example example = new Example(Examinee.class);
        example.or().andEqualTo("loginName", userName).andEqualTo("loginPwd", password).
                andEqualTo("status", "1");
        List<Examinee> eeList = selectByExample(example);
        return (eeList == null || eeList.size() < 1) ? null : eeList.get(0);
    }

    @Override
    public boolean userNameIsExist(String userName) {
        Example example = new Example(Examinee.class);
        example.or().andEqualTo("loginName", userName).andNotEqualTo("status", "4");
        List<Examinee> eeList = selectByExample(example);
        return ToolUtil.isNotEmpty(eeList);
    }
}
