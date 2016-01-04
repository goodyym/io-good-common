package com.good.model;

import com.good.common.ExIdentity;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by Administrator
 * Date:2015/12/29
 */
public class GoodUser extends ExIdentity implements Serializable {

    private static final long serialVersionUID = -8507359470837800971L;

    public static String tableName = "tb_good_user";

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

    //��ô����дhashcode��tostring��
    //http://www.cnblogs.com/lovingprince/archive/2007/04/02/2166450.html
    //http://blog.csdn.net/lishehe/article/details/45224181
    //http://blog.csdn.net/zhuojiajin/article/category/2861049
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
