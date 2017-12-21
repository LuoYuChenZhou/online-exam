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

}
