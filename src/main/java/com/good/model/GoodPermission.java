package com.good.model;

import com.good.common.ExIdentity;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by Administrator
 * Date:2016/1/2
 */
public class GoodPermission extends ExIdentity implements Serializable {
    private static final long serialVersionUID = -8591132586936453531L;

    public static final String tableName = "tb_good_permission";

    private String url;

    private int rolePermissionId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(int rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
