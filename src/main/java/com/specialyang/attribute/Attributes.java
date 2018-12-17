package com.specialyang.attribute;

import com.specialyang.session.Session;
import io.netty.util.AttributeKey;

/**
 * Created by Fan Yang in 2018/11/30 10:46 AM.
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
