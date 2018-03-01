package com.lycz.service.grade.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.controller.common.FixPageInfo;
import com.lycz.controller.common.ToolUtil;
import com.lycz.dao.GradeMapper;
import com.lycz.model.Grade;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.grade.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GradeServiceImpl extends BaseServiceTk<Grade> implements GradeService {

    @Resource
    private GradeMapper gradeMapper;

    @Override
    public FixPageInfo<Map<String, Object>> getGradeListByNameUser(String searchGradeName, String userId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> gardeList = gradeMapper.getGradeListByNameUser(searchGradeName, userId);
        if (ToolUtil.isEmpty(gardeList)) {
            return null;
        } else {
            return new FixPageInfo<>(gardeList);
        }
    }
}
