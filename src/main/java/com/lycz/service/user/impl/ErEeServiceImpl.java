package com.lycz.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.dao.ErEeMapper;
import com.lycz.model.ErEe;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.user.ErEeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ErEeServiceImpl extends BaseServiceTk<ErEe> implements ErEeService {

    @Resource
    private ErEeMapper erEeMapper;

    @Override
    public FixPageInfo<Map<String, Object>> getExamineeNoRelation(String searchEeName, String searchEeNo, Integer page, Integer limit, String userId) {
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> eeList = erEeMapper.getExamineeNoRelation(searchEeName, searchEeNo, userId);
        if (ToolUtil.isEmpty(eeList)) {
            return null;
        } else {
            return new FixPageInfo<>(eeList);
        }
    }
}
