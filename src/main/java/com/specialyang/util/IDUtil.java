package com.specialyang.util;

import java.util.UUID;

/**
 * Created by Fan Yang in 2018/12/1 3:51 PM.
 */
public class IDUtil {

    public static String generatorId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
