package com.lycz.service.base;

public interface TokenService {

    String createToken(Object entity);

    void destroyToken(String token);
}
