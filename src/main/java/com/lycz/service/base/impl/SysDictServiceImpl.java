package com.lycz.service.base.impl;

import com.lycz.model.SysDict;
import com.lycz.service.base.SysDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysDictServiceImpl extends BaseServiceTk<SysDict> implements SysDictService {

}
