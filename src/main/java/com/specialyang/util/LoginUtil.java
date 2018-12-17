package com.specialyang.util;

import com.specialyang.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
/**
 * Created by Fan Yang in 2018/11/30 10:50 AM.
 */
public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
