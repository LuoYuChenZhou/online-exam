package com.lycz.service.base;

import java.util.Map;

public interface TokenService {

    String createToken(Object entity);

    void destroyToken(String token);

    Map<String, Object> getTokenMap(String token);

    String getUserId(String token);
}
