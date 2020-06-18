package com.mingsoft.nvssauthor.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: nvss-actuator
 * @description: 格式化工具类
 * @author: xq
 * @create: 2020-04-02 20:54
 **/
public class FormatUtils {
    /**
     * 日期格式化  年月日 时分秒
     */

    public static String forMatYearMonthYearHMS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        return sdf.format(date);
    }
}
