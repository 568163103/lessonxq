package com.mingsoft.nvssauthor.utils;

import java.security.MessageDigest;

/**
 * @ClassName
 * @Description
 * @Auror
 * @Date
 * @Version
 **/
public class MD5 {
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }
    public static void main(String[] args) {
        System.out.println(md5("31119@qq.com"+"123456"));
        System.out.println(md5("e10adc3949ba59abbe56e057f20f883e1207"));
    }
}
