package com.lycz.service.paper;

import com.lycz.model.Papers;
import com.lycz.service.base.IBaseServiceTk;

import java.util.List;
import java.util.Map;

public interface PapersService extends IBaseServiceTk<Papers> {

    List<Map<String,Object>> searchExaminationName(String papersName, String teachersId);

}
