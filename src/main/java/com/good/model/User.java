package com.good.model;

import com.good.common.ExIdentity;

import java.io.Serializable;

/**
 * Created by Administrator
 * Date:2015/12/29
 */
public class User extends ExIdentity implements Serializable {

    private static final long serialVersionUID = -8507359470837800971L;

    public static String tableName = "tb_user";

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //怎么样重写hashcode和tostring的
}
