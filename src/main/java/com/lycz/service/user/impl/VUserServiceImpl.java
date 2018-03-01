package com.lycz.service.user.impl;

import com.lycz.model.VUser;
import com.lycz.service.base.impl.BaseServiceTk;
import com.lycz.service.user.VUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VUserServiceImpl extends BaseServiceTk<VUser> implements VUserService {
}
