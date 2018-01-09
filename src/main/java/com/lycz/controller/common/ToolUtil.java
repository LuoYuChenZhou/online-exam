package com.lycz.controller.common;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class ToolUtil extends StringUtils {

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isEmpty(Map map) {
        return map == null;
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /**
     * 如果比较字符串中有一个与被计较字符串相同，返回true
     *
     * @param compare 被比较字符串
     * @param ss      数目不定的比较字符串
     */
    public static boolean anyEqual(String compare, String... ss) {
        if (compare == null) return false;
        for (String s : ss) {
            if (compare.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
