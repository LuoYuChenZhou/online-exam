package com.lycz.service.paper.impl;

import com.lycz.dao.PapersMapper;
import com.lycz.model.Papers;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.paper.PapersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PapersServiceImpl extends BaseServiceTk<Papers> implements PapersService {
    @Resource
    private PapersMapper papersMapper;

    @Override
    public List<Map<String, Object>> searchExaminationName(String papersName, String teachersId) {
        return papersMapper.searchExaminationName(papersName,teachersId);
    }
}
