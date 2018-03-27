package com.lycz.service.base.impl;

import com.lycz.dao.CommonMapper;
import com.lycz.service.base.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/3/19 11:49
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private CommonMapper commonMapper;

    @Override
    public void setSortNoByLineNum(String tableName, String operateId, int sortNo, int orderTime, String otherWhere) {
        commonMapper.setSortNoByLineNum(tableName, operateId, sortNo, orderTime, otherWhere);
    }
}
