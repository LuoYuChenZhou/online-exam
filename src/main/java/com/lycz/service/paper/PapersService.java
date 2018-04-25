package com.lycz.service.paper;

import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.model.Papers;
import com.lycz.service.base.IBaseServiceTk;

import java.util.List;
import java.util.Map;

public interface PapersService extends IBaseServiceTk<Papers> {

    FixPageInfo<Map<String, Object>> selectPapersByName(String papersName,
                                                        String teachersId,
                                                        Integer page,
                                                        Integer limit);

}
