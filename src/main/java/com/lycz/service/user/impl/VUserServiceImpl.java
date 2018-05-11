package com.lycz.service.user.impl;

import com.lycz.configAndDesign.ToolUtil;
import com.lycz.model.VUser;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.user.VUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VUserServiceImpl extends BaseServiceTk<VUser> implements VUserService {
    @Override
    public VUser selectByUserId(String userId) {
        Example example = new Example(VUser.class);
        example.or().andEqualTo("userId", userId);
        List<VUser> vList = selectByExample(example);
        if (ToolUtil.isEmpty(vList)) {
            return null;
        } else {
            return vList.get(0);
        }
    }
}
