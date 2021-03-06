package com.lycz.service.base.impl;

import com.lycz.configAndDesign.JedisUtil;
import com.lycz.configAndDesign.ToolUtil;
import com.lycz.model.Examinee;
import com.lycz.model.Examiner;
import com.lycz.service.base.TokenService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private String tokenPre = ToolUtil.getProperty("config/globalConfig.properties", "TOKEN_PRE");

    @Override
    public String createToken(Object entity) {
        Map<String, Object> tokenMap = new HashMap<>();

        if (entity == null) {
            return null;
        } else if (entity instanceof Examinee || entity instanceof Examiner) {
            tokenMap = ToolUtil.transBean2Map(entity);
            tokenMap.remove("loginPwd");
            tokenMap.put("userType", entity instanceof Examinee ? "Examinee" : "Examiner");
        } else if (entity.equals("sysLog")) {
            tokenMap.put("id", "sys_id");
            tokenMap.put("realName", "系统管理员");
            tokenMap.put("userType", ToolUtil.getProperty("config/sysLg.properties", "sys_user_type"));
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
    public Map<String, Object> getTokenMap(String token) {
        return JedisUtil.getMap(tokenPre + token);
    }

    @Override
    public String getUserId(String token) {
        Map<String, Object> tokenMap = getTokenMap(token);
        return (String) tokenMap.get("id");
    }
}
