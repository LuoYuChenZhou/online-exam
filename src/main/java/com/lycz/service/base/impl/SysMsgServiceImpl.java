package com.lycz.service.base.impl;

import com.lycz.configAndDesign.ToolUtil;
import com.lycz.dao.SysMsgMapper;
import com.lycz.model.ErEe;
import com.lycz.model.SysMsg;
import com.lycz.service.base.SysDictService;
import com.lycz.service.base.SysMsgService;
import com.lycz.service.user.ErEeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SysMsgServiceImpl extends BaseServiceTk<SysMsg> implements SysMsgService {

    @Resource
    private SysDictService sysDictService;
    @Resource
    private SysMsgMapper sysMsgMapper;
    @Resource
    private ErEeService erEeService;

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
    public boolean addEeMsg(String sendId, String msg, String msgType) throws Exception {
        String dictId = sysDictService.selectIdByCodeValue("msgType", msgType);

        Example example = new Example(ErEe.class);
        example.or().andEqualTo("examinerId", sendId).andEqualTo("curStatus", "3");
        List<ErEe> erEeList = erEeService.selectByExample(example);

        if (ToolUtil.isEmpty(erEeList)) {
            return true;
        }

        List<SysMsg> msgList = new ArrayList<>();
        for (ErEe erEe : erEeList) {
            SysMsg sysMsg = new SysMsg();
            sysMsg.setId(UUID.randomUUID().toString());
            sysMsg.setSendId(sendId);
            sysMsg.setReceiveId(erEe.getExamineeId());
            sysMsg.setMsg(msg);
            sysMsg.setMsgType(dictId);
            sysMsg.setStatus("0");
            sysMsg.setCreateTime(new Date());
            msgList.add(sysMsg);
        }

        return sysMsgMapper.batchInsertMsg(msgList) > 0;
    }

    @Override
    public List<Map<String, Object>> getMsgListByTimeUser(String searchTime, String userId) {
        return sysMsgMapper.getMsgListByTimeUser(searchTime, userId);
    }
}
