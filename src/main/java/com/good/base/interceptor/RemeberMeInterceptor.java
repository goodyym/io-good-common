package com.good.base.interceptor;

import com.iotekclass.buscompoents.model.permission.ManageAccount;
import com.iotekclass.buscompoents.service.permission.ManageAccountService;
import com.iotekclass.common.constants.UserConstants;
import com.iotekclass.operation.web.security.CustomManageShiroSession;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 自动登录拦截器
 * 
 * @author ZengAihui
 * 
 */
public class RemeberMeInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

    @Autowired
	private ManageAccountService manageAccountService;

	@Autowired
	private CustomManageShiroSession customManageShiroSession;

	private static Logger logger = LoggerFactory.getLogger(RemeberMeInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated() && subject.isRemembered()) {
			Object principal = subject.getPrincipal();
			String loginName = String.valueOf(principal);
			logger.info("remeber me to login by:" + loginName);
//			User user = userService.findByUsername(loginName);
			ManageAccount manageAccount = manageAccountService.getByNameAndPwd(loginName, "");
			HttpSession session = ((HttpServletRequest) ((WebSubject) SecurityUtils.getSubject()).getServletRequest())
					.getSession();
			Object sessionUser = session.getAttribute(UserConstants.LOGIN_USER_KEY);
			logger.debug(sessionUser + "");
			if (sessionUser == null) {
				customManageShiroSession.generateUserSessions(manageAccount);
			}
		}
		return invocation.invoke();
	}
}
