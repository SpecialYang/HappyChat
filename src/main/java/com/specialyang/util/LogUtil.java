package com.specialyang.util;

import java.util.Date;

/**
 * Created by Fan Yang in 2018/11/30 9:35 AM.
 */
public class LogUtil {

    public static void print(String msg) {
        System.out.println(new Date() + ": " + msg);
    }

    public static void errPrint(String msg) {
        System.err.println(new Date() + ": " + msg);
    }
}
