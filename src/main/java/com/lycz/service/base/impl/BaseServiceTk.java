package com.lycz.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.service.base.IBaseServiceTk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Sholybell
 * @说明：此为Mapper3通用的Service抽象接口，其他Serice可以在继承此Service的基础上， 实现自己定义的接口的方法，自定义的方法需要另外使用Mapper.xml
 */

@Service
public abstract class BaseServiceTk<T> implements IBaseServiceTk<T> {


    @Autowired
    protected Mapper<T> mapper;

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     */
    public List<T> select(T t) {
        return mapper.select(t);
    }

    /**
     * 根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号
     */
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    /**
     * 查询全部结果，select(null)方法能达到同样的效果
     */
    public List<T> selectAll() {
        return mapper.select(null);
    }

    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     */
    public T selectOne(T record) {
        return mapper.selectOne(record);
    }

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     */
    public int selectCount(T record) {
        return mapper.selectCount(record);
    }

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     */
    public int save(T entity) {
        return mapper.insert(entity);
    }

    /**
     * 保存一个实体，null的属性不会保存，会使用数据库默认值
     */
    public int insertSelective(T record) {
        return mapper.insert(record);
    }

    /**
     * 根据主键更新实体全部字段，null值会被更新
     */
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    /**
     * 根据主键更新属性不为null的值
     */
    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据实体属性作为条件进行删除，查询条件使用等号
     */
    public int delete(T record) {
        return mapper.delete(record);
    }

    /**
     * 根据主键字段进行删除，方法参数必须包含完整的主键属性
     */
    public int deleteByPrimaryKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    /**
     * 单表分页查询
     */
    public List<T> selectPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        // Spring4支持泛型注入
        return mapper.select(null);
    }

}