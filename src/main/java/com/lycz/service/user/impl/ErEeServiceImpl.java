package com.lycz.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.dao.ErEeMapper;
import com.lycz.model.EeGrade;
import com.lycz.model.ErEe;
import com.lycz.service.base.CommonService;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.grade.EeGradeService;
import com.lycz.service.user.ErEeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ErEeServiceImpl extends BaseServiceTk<ErEe> implements ErEeService {

    @Resource
    private ErEeMapper erEeMapper;
    @Resource
    private EeGradeService eeGradeService;
    @Resource
    private CommonService commonService;

    @Override
    public FixPageInfo<Map<String, Object>> getExamineeNoRelation(String searchString, Integer page, Integer limit, String userId) {
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> eeList = erEeMapper.getExamineeNoRelation(searchString, userId);
        if (ToolUtil.isEmpty(eeList)) {
            return null;
        } else {
            return new FixPageInfo<>(eeList);
        }
    }

    @Override
    public boolean addEe(ErEe erEe, String gradeId, Integer sortNo) {
        if (ToolUtil.isEmpty(gradeId) || Objects.equals("noAddToGrade", gradeId)) {
            return insertSelective(erEe) > 0;
        }

        //如果传入了班级id，则添加到考生班级关系表并排序
        String newId = UUID.randomUUID().toString();
        sortNo = ToolUtil.isEmpty(sortNo) ? 1 : sortNo;
        EeGrade eeGrade = new EeGrade();
        eeGrade.setId(newId);
        eeGrade.setGradeId(gradeId);
        eeGrade.setSortNo(sortNo);
        eeGrade.setEeId(erEe.getExamineeId());
        if (insertSelective(erEe) < 1 || eeGradeService.insertSelective(eeGrade) < 1) {
            return false;
        }

        commonService.setSortNoByLineNum("ee_grade", newId, sortNo, 0, " grade_id = '" + gradeId + "' ");
        return true;
    }
}
