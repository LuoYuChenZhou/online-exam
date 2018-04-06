package com.lycz.service.user;

import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.model.ErEe;
import com.lycz.service.base.IBaseServiceTk;

import java.util.Map;

public interface ErEeService extends IBaseServiceTk<ErEe> {

    FixPageInfo<Map<String, Object>> getExamineeNoRelation(String searchString, Integer page, Integer limit, String userId);

    boolean addEe(ErEe erEe, String gradeId, Integer sortNo);
}
