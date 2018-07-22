package com.lycz.service.base.impl;

import com.lycz.dao.CommonMapper;
import com.lycz.service.base.CommonService;
import com.lycz.service.base.TokenService;
import net.sf.json.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/3/19 11:49
 */
@Service
public class CommonServiceImpl implements CommonService {

    private Logger log = LogManager.getLogger(CommonServiceImpl.class);

    @Resource
    private CommonMapper commonMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private TokenService tokenService;

    @Override
    public void setSortNoByLineNum(String tableName, String operateId, int sortNo, int orderTime, String otherWhere) {
        commonMapper.setSortNoByLineNum(tableName, operateId, sortNo, orderTime, otherWhere);
    }

    @Override
    public void sendActiveMQMessage(String type, String message, String token) {
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("message", message);
        messageMap.put("userId", tokenService.getUserId(token));
        messageMap.put("operate", "info");
        if (Objects.equals(type, "topic")) {
            jmsTemplate.convertAndSend(JSONObject.fromObject(messageMap).toString());
        } else {
            jmsTemplate.convertAndSend("online.exam.queue", JSONObject.fromObject(messageMap).toString());
        }
    }

    @Override
    public void sendErEeAMQMessage(String message, String erId, String eeId, String type) {
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("message", message);
        messageMap.put("erId", erId);
        messageMap.put("eeId", eeId);
        messageMap.put("operate", type);
        jmsTemplate.convertAndSend(JSONObject.fromObject(messageMap).toString());
    }
}
