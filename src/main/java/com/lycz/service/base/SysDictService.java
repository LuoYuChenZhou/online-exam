package com.lycz.service.base;

import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.model.SysDict;

import java.util.Map;

public interface SysDictService extends IBaseServiceTk<SysDict> {
    /**
     * 根据字典编码和字典值获取字典id(此方法只查询状态为1的)
     */
    String selectIdByCodeValue(String dictCode, String dictValue) throws Exception;

    FixPageInfo<Map<String, Object>> getDictListByNameValueCodeUpperId(String searchString, String upperId, Integer page, Integer limit);

    /**
     * 查询此id下是否存在子级字典
     */
    boolean sonDictExist(String upperId);
}
