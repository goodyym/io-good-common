package com.good.base.interceptor;

import com.iotekclass.buscompoents.model.permission.ManageAccount;
import com.iotekclass.common.constants.UserConstants;
import com.iotekclass.common.util.CookieUtils;
import com.iotekclass.components.service.cache.MemCacheService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ZengAihui
 *         单一登录拦截器
 */
public class SingleLoginInterceptor extends AbstractInterceptor {

	private static Logger logger = LoggerFactory.getLogger(SingleLoginInterceptor.class);

	private static final long serialVersionUID = 1L;

	private static final String ALERT_COOKIE = "alert_info";

	@Autowired
	private MemCacheService memManager;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		HttpServletRequest request = ServletActionContext.getRequest();
		if (subject.isAuthenticated() && (subject.getPrincipal() != null)) {
			String loginName = String.valueOf(subject.getPrincipal());
			String sessionId = request.getRequestedSessionId();
			HttpSession session = request.getSession();
			if (session != null) {
				ManageAccount manageAccount = (ManageAccount) session.getAttribute(UserConstants.LOGIN_USER_KEY);
				loginName = manageAccount == null ? "" : String.valueOf(manageAccount.getId());
			}
			if (memManager.exists(loginName + "_operation-consumer")
					&& !memManager.get(loginName + "_operation-consumer").toString().equals(sessionId)) {
				// 判断是否已经保存该登录用户的信息 并且 如果是同一个用户进行重复登录那么允许登录
				logger.info("用户已在其他地方登录。。。");
				CookieUtils.setCookie(ServletActionContext.getResponse(), ALERT_COOKIE, 60, "您已在其他地方登录!");
				return "logout";
			}
		}
		return invocation.invoke();
	}
}
