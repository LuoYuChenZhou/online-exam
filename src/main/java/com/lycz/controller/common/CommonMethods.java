package com.lycz.controller.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonMethods {

    public static String getProperty(String fileName, String property) {
        Properties prop = new Properties();
        InputStream inStream = CommonMethods.class.getResourceAsStream("/" + fileName);
        String propertiesValue = "";
        try {
            prop.load(inStream);
            propertiesValue = prop.getProperty(property);
        } catch (IOException e) {
            System.out.println("conference配置读取失败！");
            e.printStackTrace();
        }
        return propertiesValue;
    }

    public static String toJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
