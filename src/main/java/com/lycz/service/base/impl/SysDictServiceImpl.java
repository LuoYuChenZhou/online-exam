package com.lycz.service.base.impl;

import com.lycz.configAndDesign.ToolUtil;
import com.lycz.model.SysDict;
import com.lycz.service.base.SysDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysDictServiceImpl extends BaseServiceTk<SysDict> implements SysDictService {

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
}
