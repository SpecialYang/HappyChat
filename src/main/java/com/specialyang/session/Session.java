package com.specialyang.session;

import lombok.Data;

/**
 * Created by Fan Yang in 2018/11/30 5:56 PM.
 */
@Data
public class Session {


    //用户唯一表示
    private String userId;

    private String username;

    public Session(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
