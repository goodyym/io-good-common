package com.good.service;


import com.good.model.GoodRole;

import java.util.List;

/**
 * Created by Administrator
 * Date:2016/1/2
 */
public interface GoodRoleService {

    List<GoodRole> getRoleByUser(int userId);

}
