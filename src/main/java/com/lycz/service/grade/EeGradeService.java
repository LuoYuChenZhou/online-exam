package com.lycz.service.grade;

import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.model.EeGrade;
import com.lycz.service.base.IBaseServiceTk;

import java.util.List;
import java.util.Map;

public interface EeGradeService extends IBaseServiceTk<EeGrade> {

    FixPageInfo<Map<String, Object>> getEeListByNameNoClass(String erId, String searchClass, String searchString, Integer page, Integer limit);

    List<Map<String, Object>> getNoEeGradeList(String erId, String eeId);

    Integer deleteByEeEr(String erId, String eeId);

    int buildEeGradeByStatus(String erId, String eeId);

    List<String> getGradeListByErEe(String erId, String eeId);
}
