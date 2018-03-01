package com.lycz.service.grade;

import com.lycz.controller.common.FixPageInfo;
import com.lycz.model.Grade;
import com.lycz.service.base.IBaseServiceTk;

import java.util.List;
import java.util.Map;

public interface GradeService extends IBaseServiceTk<Grade> {

    FixPageInfo<Map<String, Object>> getGradeListByNameUser(String searchGradeName, String userId, Integer page, Integer limit);
}
