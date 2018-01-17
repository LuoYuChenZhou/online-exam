package com.lycz.controller.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommonMethods {

    private static Logger logger = LogManager.getLogger();

    /**
     * 读取配置文件中某属性的值
     *
     * @param fileName 文件名
     * @param property 属性名
     * @return 属性的值
     */
    public static String getProperty(String fileName, String property) {
        Properties prop = new Properties();
        String path = CommonMethods.class.getResource("/" + fileName).getPath();
        InputStream inStream;
        String propertiesValue;
        try {
            inStream = new FileInputStream(path);
            prop.load(inStream);
            propertiesValue = prop.getProperty(property);
        } catch (FileNotFoundException e) {
            logger.error("配置文件路径错误：{}", fileName);
            return null;
        } catch (IOException e) {
            logger.error("配置读取失败！");
            e.printStackTrace();
            return null;
        }
        return propertiesValue;
    }

    /**
     * 实体类对象转化为Map
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if (value != null) {
                        map.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("transBean2Map Error:{} ", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
}
