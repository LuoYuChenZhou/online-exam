package com.lycz.service.other.impl;

import com.lycz.model.Papers;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.other.PapersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PapersServiceImpl extends BaseServiceTk<Papers> implements PapersService {
}
