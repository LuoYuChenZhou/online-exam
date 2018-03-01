package com.lycz.service.base;

import java.util.Map;

public interface TokenService {

    String createToken(Object entity);

    void destroyToken(String token);

    Map<String, Object> getToken(String token);

    String getUserId(String token);
}
