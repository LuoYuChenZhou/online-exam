package com.lycz.service.base;

import com.lycz.model.SysDict;

public interface SysDictService extends IBaseServiceTk<SysDict> {
    /**
     * 根据字典编码和字典值获取字典id(此方法只查询状态为1的)
     */
    String selectIdByCodeValue(String dictCode, String dictValue) throws Exception;
}
