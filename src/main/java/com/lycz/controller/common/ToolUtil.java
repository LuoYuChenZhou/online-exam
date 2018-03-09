package com.lycz.controller.common;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class ToolUtil extends StringUtils {

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
     * @param compare 被比较字符串
     * @param ss      数目不定的比较字符串
     */
    @Contract("null, _ -> fail")
    public static boolean anyEqual(String compare, String... ss) throws Exception {
        if (compare == null) throw new Exception("被比较字符串不能为空");
        for (String s : ss) {
            if (compare.equals(s)) {
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
}
