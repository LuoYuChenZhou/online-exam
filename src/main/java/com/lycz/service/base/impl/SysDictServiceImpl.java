package com.lycz.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.lycz.configAndDesign.FixPageInfo;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.dao.SysDictMapper;
import com.lycz.model.SysDict;
import com.lycz.service.base.SysDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysDictServiceImpl extends BaseServiceTk<SysDict> implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public String selectIdByCodeValue(String dictCode, String dictValue) throws Exception {
        Example example = new Example(SysDict.class);
        example.or().andEqualTo("dictCode", dictCode)
                .andEqualTo("dictValue", dictValue)
                .andEqualTo("status", "1");
        List<SysDict> list = selectByExample(example);
        if (ToolUtil.isEmpty(list)) {
            return null;
        } else if (list.size() > 1) {
            throw new Exception("!!noShow!!code为" + dictCode + "value为" + dictValue + "的字典存在多个！");
        } else {
            return list.get(0).getId();
        }
    }

    @Override
    public FixPageInfo<Map<String, Object>> getDictListByNameValueCodeUpperId(String searchString, String upperId, Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Map<String, Object>> tempList = sysDictMapper.getDictListByNameValueCodeUpperId(searchString, upperId);
        if (ToolUtil.isEmpty(tempList)) {
            return null;
        }
        return new FixPageInfo<>(tempList);
    }

    @Override
    public boolean sonDictExist(String upperId) {
        Example example = new Example(SysDict.class);
        example.or().andEqualTo("upperId", upperId).andNotEqualTo("status", "4");
        List<SysDict> tempList = selectByExample(example);
        return ToolUtil.isNotEmpty(tempList);
    }
}
