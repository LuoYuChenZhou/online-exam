package com.lycz.service.grade.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.dao.EeGradeMapper;
import com.lycz.model.EeGrade;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.grade.EeGradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EeGradeServiceImpl extends BaseServiceTk<EeGrade> implements EeGradeService {
    @Resource
    private EeGradeMapper eeGradeMapper;

    @Override
    public FixPageInfo<Map<String, Object>> getEeListByNameNoClass(String searchClass, String searchString, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> eeList = eeGradeMapper.getEeListByNameNoClass(searchClass, searchString);
        if (ToolUtil.isEmpty(eeList)) {
            return null;
        } else {
            return new FixPageInfo<>(eeList);
        }
    }
}
