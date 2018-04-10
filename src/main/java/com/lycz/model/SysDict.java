package com.lycz.model;

import javax.persistence.*;

@Table(name = "sys_dict")
public class SysDict {
    @Id
    private String id;

    /**
     * 字典值
     */
    @Column(name = "dict_name")
    private String dictName;

    /**
     * 字典编码
     */
    @Column(name = "dict_code")
    private String dictCode;

    /**
     * 字典值
     */
    @Column(name = "dict_value")
    private String dictValue;

    /**
     * 父级id（0-顶级字典）
     */
    @Column(name = "upper_id")
    private String upperId;

    /**
     * 排序号
     */
    @Column(name = "sort_no")
    private Integer sortNo;

    /**
     * 状态（014）
     */
    private String status;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取字典值
     *
     * @return dict_name - 字典值
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * 设置字典值
     *
     * @param dictName 字典值
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * 获取字典编码
     *
     * @return dict_code - 字典编码
     */
    public String getDictCode() {
        return dictCode;
    }

    /**
     * 设置字典编码
     *
     * @param dictCode 字典编码
     */
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    /**
     * 获取字典值
     *
     * @return dict_value - 字典值
     */
    public String getDictValue() {
        return dictValue;
    }

    /**
     * 设置字典值
     *
     * @param dictValue 字典值
     */
    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    /**
     * 获取父级id（0-顶级字典）
     *
     * @return upper_id - 父级id（0-顶级字典）
     */
    public String getUpperId() {
        return upperId;
    }

    /**
     * 设置父级id（0-顶级字典）
     *
     * @param upperId 父级id（0-顶级字典）
     */
    public void setUpperId(String upperId) {
        this.upperId = upperId;
    }

    /**
     * 获取排序号
     *
     * @return sort_no - 排序号
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * 设置排序号
     *
     * @param sortNo 排序号
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * 获取状态（014）
     *
     * @return status - 状态（014）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态（014）
     *
     * @param status 状态（014）
     */
    public void setStatus(String status) {
        this.status = status;
    }
}