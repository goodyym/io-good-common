package com.good.base.interceptor;

import com.iotekclass.common.constants.UserConstants;
import com.iotekclass.common.util.Collections3;
import com.iotekclass.components.service.cache.MemCacheService;
import com.iotekclass.operation.utils.ParseShiroDefaultMap;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @Description 用户权限拦截器
 * @author wangfengbao
 * @date 2015年5月8日 下午4:02:58
 *
 */
public class UserPermissionInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    private static final String ADMIN_ROLE = "1";

    @Autowired
    MemCacheService memManager;

    @Autowired
    ParseShiroDefaultMap parseShiroDefaultMap;

    @SuppressWarnings("unchecked")
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        HttpServletRequest request = ServletActionContext.getRequest();
        String targetUrl = request.getRequestURI().trim().replace("/operation-consumer/", "/");

        //判断缓存中是否具有shiro权限数据,如果没有，解析shiro配置文件获取相应权限配置信息
        if (!memManager.exists(ParseShiroDefaultMap.SHIRO_DEFAULT_STR)) {
            memManager.add(ParseShiroDefaultMap.SHIRO_DEFAULT_STR, parseShiroDefaultMap.parseXml());
        } else {
            memManager.replace(ParseShiroDefaultMap.SHIRO_DEFAULT_STR, parseShiroDefaultMap.parseXml());
        }

        String urlsList = ObjectUtils.toString(memManager.get(ParseShiroDefaultMap.SHIRO_DEFAULT_STR));

        if (!urlsList.contains(targetUrl)
                && (!targetUrl.contains("/web/js") || !targetUrl.contains("/web/css")
                || !targetUrl.contains("/web/images") || !targetUrl.contains("/web/pages"))) {
            //判断用户是否具有权限
            if (subject.isAuthenticated() && (subject.getPrincipal() != null)) {
                int userId = Integer.parseInt(request.getSession().getAttribute(UserConstants.LOGIN_USER_ID_KEY)
                        .toString());
                String role = request.getSession().getAttribute(UserConstants.ACCOUNT_ROLE_KEY).toString();
                //获取登录用户的权限信息
                //判断用户是否有访问权限
                if (!ADMIN_ROLE.equals(role)) {
                    if (memManager.exists(userId + UserConstants.ACCOUNT_PERMISSION_KEY)) {
                        List<String> urls = (List<String>) memManager
                                .get(userId + UserConstants.ACCOUNT_PERMISSION_KEY);
                        if (Collections3.isNotEmpty(urls)) {
                            if (!urls.contains(targetUrl)) {
                                return "nopermission";
                            }
                        }
                    }
                }

            }
        }
        return invocation.invoke();
    }
}
