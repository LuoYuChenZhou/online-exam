package com.lycz.service.base;

import java.util.Map;

/**
 * 通用方法对应的service层
 *
 * @author lizhenqing
 * @version 1.0
 * @data 2018/3/19 11:49
 */
public interface CommonService {
    /**
     * 设置排序号(将操作id设置为指定排序号，其它行的排序号跟着改变)
     *
     * @param tableName 操作的表名
     * @param operateId 操作id
     * @param sortNo    操作id对应的排序号
     * @param orderTime  默认为按照原先的排序号顺序设置新的排序号，
     *                   如果此字段为1，则添加按照修改时间倒序排列，没有modify_time字段的不要传1
     * @param otherWhere 其它的查询条件，如（upperId = 1）
     */
    void setSortNoByLineNum(String tableName, String operateId, int sortNo, int orderTime, String otherWhere);
}
