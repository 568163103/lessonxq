package com.xq.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class StringUtil {
    /**
     * String 字符串转JSON数组
     * @param str
     * @return
     */
    public static JSONArray strToJSONArray(String str) {
        return JSON.parseArray(str.substring(1, str.length() - 1).replaceAll("\\\\", ""));
    }
}
