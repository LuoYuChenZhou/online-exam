package com.lycz.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.dao.ExamineeMapper;
import com.lycz.model.Examinee;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.user.ExamineeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ExamineeServiceImpl extends BaseServiceTk<Examinee> implements ExamineeService {
    @Resource
    private ExamineeMapper examineeMapper;

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

    @Override
    public FixPageInfo<Map<String, Object>> getEeListByNameNoClass(String searchClass, String examineeName, String examineeNum, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> eeList = examineeMapper.getEeListByNameNoClass(searchClass, examineeName, examineeNum);
        if (ToolUtil.isEmpty(eeList)) {
            return null;
        } else {
            return new FixPageInfo<>(eeList);
        }
    }
}
