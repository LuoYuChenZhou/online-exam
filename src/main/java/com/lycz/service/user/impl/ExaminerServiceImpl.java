package com.lycz.service.user.impl;

import com.lycz.controller.common.ToolUtil;
import com.lycz.model.Examiner;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.user.ExaminerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExaminerServiceImpl extends BaseServiceTk<Examiner> implements ExaminerService {

    @Override
    public Examiner erLogin(String userName, String password) {
        Example example = new Example(Examiner.class);
        example.or().andEqualTo("loginName", userName).andEqualTo("loginPwd", password).
                andEqualTo("status", "1");
        List<Examiner> erList = selectByExample(example);
        return (erList == null || erList.size() < 1) ? null : erList.get(0);
    }

    @Override
    public boolean userNameIsExist(String userName) {
        Example example = new Example(Examiner.class);
        example.or().andEqualTo("loginName", userName).andNotEqualTo("status", "4");
        List<Examiner> eeList = selectByExample(example);
        return ToolUtil.isNotEmpty(eeList);
    }
}
