package com.lycz.service.base.impl;

import com.lycz.controller.common.CommonMethods;
import com.lycz.controller.common.JedisUtil;
import com.lycz.model.Examinee;
import com.lycz.model.Examiner;
import com.lycz.service.base.TokenService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private String tokenPre = "online-exam-token:";

    @Override
    public String createToken(Object entity) {
        Map<String, Object> tokenMap = new HashMap<>();

        if (entity == null) {
            return null;
        } else if (entity instanceof Examinee || entity instanceof Examiner) {
            tokenMap = CommonMethods.transBean2Map(entity);
            tokenMap.remove("loginPwd");
        } else if (entity.equals("sysLog")) {
            tokenMap.put("id", "sys_id");
            tokenMap.put("realName", "超级管理员");
            tokenMap.put("manageToken", "随便来点什么，反正普通用户没有");
        } else {
            return null;
        }

        String uuid = UUID.randomUUID().toString();
        JedisUtil.setMap(tokenPre + uuid, tokenMap);
        return uuid;
    }

    @Override
    public void destroyToken(String token) {
        JedisUtil.delKey(tokenPre + token);
    }

    @Override
    public Map<String, Object> getToken(String token) {
        return JedisUtil.getMap(tokenPre + token);
    }
}
