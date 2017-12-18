package com.lycz.service.base.impl;

import com.lycz.service.base.TokenService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String createToken(Map map) {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
