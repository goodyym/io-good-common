package com.good.service;

import com.good.model.Page;
import com.good.model.TestPageModel;

import java.util.Map;

/**
 * Description: uuuuuuuuuuuuuuuuu
 * Author: tretert
 * Time:${TTME}
 */
public interface ListPageService {

    Page<TestPageModel> getListPage(Map<String,Object> params,Page<TestPageModel> page);

}
