package com.good.service;


import com.good.model.GoodPermission;

import java.util.List;

/**
 * Created by Administrator
 * Date:2016/1/2
 */
public interface GoodPermissionService {

    List<GoodPermission> getPermissionByRole(int roleId);

}
