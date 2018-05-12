package com.lycz.service.base.impl;

import com.lycz.dao.SysMsgMapper;
import com.lycz.model.SysMsg;
import com.lycz.service.base.SysDictService;
import com.lycz.service.base.SysMsgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysMsgServiceImpl extends BaseServiceTk<SysMsg> implements SysMsgService {

    @Resource
    private SysDictService sysDictService;
    @Resource
    private SysMsgMapper sysMsgMapper;

    @Override
    public boolean addMsg(String sendId, String receiveId, String msg, String msgType) throws Exception {
        String dictId = sysDictService.selectIdByCodeValue("msgType", msgType);

        SysMsg sysMsg = new SysMsg();
        sysMsg.setId(UUID.randomUUID().toString());
        sysMsg.setSendId(sendId);
        sysMsg.setReceiveId(receiveId);
        sysMsg.setMsg(msg);
        sysMsg.setMsgType(dictId);
        sysMsg.setStatus("0");
        sysMsg.setCreateTime(new Date());

        return insertSelective(sysMsg) > 0;
    }

    @Override
    public List<Map<String, Object>> getMsgListByTimeUser(String searchTime, String userId) {
        return sysMsgMapper.getMsgListByTimeUser(searchTime, userId);
    }
}
