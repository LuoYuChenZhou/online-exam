package com.lycz.configAndDesign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

public class ToolUtil extends StringUtils {

    private static Logger logger = LogManager.getLogger();

    /**
     * 传入十进制数，转化为二进制后，所有0变1，所有1变0，然后转成十进制输出
     * 本工程中用于多选题输入正确答案的和，获取错误答案的和
     *
     * @param trueOptionsValue 正确答案的和
     * @return 错误答案的和
     */
    public static Integer getWrongOptionsValue(Integer trueOptionsValue) {
        if (isEmpty(trueOptionsValue)) return null;
        StringBuilder sb = new StringBuilder();
        char[] trueC = Integer.toBinaryString(trueOptionsValue).toCharArray();
        for (char c : trueC) {
            sb.append(Objects.equals(c, '1') ? "0" : "1");
        }
        return Integer.parseInt(sb.toString(), 2);
    }

    /**
     * 读取配置文件中某属性的值
     *
     * @param fileName 文件名
     * @param property 属性名
     * @return 属性的值
     */
    public static String getProperty(String fileName, String property) {
        Properties prop = new Properties();
        String path = ToolUtil.class.getResource("/" + fileName).getPath();
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

    /**
     * 从str1变成str2要使用的步数
     */
    public static int ld(String str1, String str2) {
        int d[][]; // 矩阵
        int n = str1.length();
        int m = str2.length();
        int i; // 遍历str1的
        int j; // 遍历str2的
        char ch1; // str1的
        char ch2; // str2的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) { // 遍历str1
            ch1 = str1.charAt(i - 1);
            // 去匹配str2
            for (j = 1; j <= m; j++) {
                ch2 = str2.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }

    /**
     * str1和str2的相似度
     */
    public static double sim(String str1, String str2) {
        try {
            double ld = (double) ld(str1, str2);
            return (1 - ld / (double) Math.max(str1.length(), str2.length()));
        } catch (Exception e) {
            return 0.1;
        }
    }

    /**
     * 获取最小值
     */
    private static int min(int one, int two, int three) {
        int min = one;
        if (two < min) {
            min = two;
        }
        if (three < min) {
            min = three;
        }
        return min;
    }

    @Contract("null -> true")
    public static boolean isEmpty(Object obj) {
        if (null == obj) return true;
        if (obj instanceof String) {
            return isEmpty((String) obj);
        } else if (obj instanceof List) {
            return ((List) obj).size() == 0;
        }
        return false;
    }

    @Contract("null -> false")
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }


    /**
     * 如果比较字符串中有一个与被计较字符串相同，返回true
     *
     * @param compare 被比较字符串,允许为null
     * @param ss      数目不定的比较字符串
     */
    public static boolean anyEqual(String compare, String... ss) {
        for (String s : ss) {
            if (Objects.equals(compare, s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将字符串用0补到指定长度
     *
     * @param source 原字符串
     * @param length 指定长度
     * @return (3, 3) -> 003
     */
    @NotNull
    public static String addZero(String source, int length) throws Exception {
        if (isEmpty(source)) throw new Exception("原字符串不能为空");
        StringBuilder targetStr = new StringBuilder(source);
        int sourceLength = source.length();
        if (sourceLength < length) {
            for (int i = sourceLength; i < length; i++) {
                targetStr.insert(0, "0");
            }
        }
        return targetStr.toString();
    }

    /**
     * 将列表根据父级id分级
     *
     * @param sourceList 原列表
     * @param upperId    父级id
     * @param idName     作为父级id的名字，不传默认id
     * @param upperName  父级id在map中的名字，不传默认为upperId
     * @ 如：t_avcenter_device表内,行a的id字段作为行b的父级id,放在b的access_device_id字段,
     * 则idName字段传入id，upperName字段传入accessDeviceId
     */
    public static List<Map<String, Object>> getRatingList(List<Map<String, Object>> sourceList,
                                                          String upperId, String idName, String upperName) throws Exception {
        List<Map<String, Object>> targetList = new ArrayList<>();

        idName = ToolUtil.isEmpty(idName) ? "id" : idName;
        upperName = ToolUtil.isEmpty(upperName) ? "upperId" : upperName;

        for (Map<String, Object> map : sourceList) {
            if (ToolUtil.isEmpty(map.get(upperName))) {
                throw new Exception("列表中没有" + upperName + "字段");
            }

            if (Objects.equals(upperId, map.get(upperName))) {
                Map<String, Object> tempMap = new HashMap<>(map);
                tempMap.put("children", getRatingList(sourceList, (String) map.get(idName), idName, upperName));
                targetList.add(tempMap);
            }
        }

        return targetList;
    }

    /**
     * 含有时间的对象转json如果报错，使用此方法规避
     *
     * @param obj 含有时间的对象
     */
    public static String convertTime(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String listStr = null;
        try {
            listStr = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return listStr;
    }

    /**
     * 如果传入是空对象，则返回""
     */
    public static Object returnEmptyIfNull(Object source) {
        if (isEmpty(source)) {
            return "";
        } else {
            return source;
        }
    }
}
