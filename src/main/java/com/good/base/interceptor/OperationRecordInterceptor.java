/**
 * 
 */
package com.good.base.interceptor;


import com.iotekclass.buscompoents.model.permission.ManageAccount;
import com.iotekclass.buscompoents.model.permission.ManageOperationRecord;
import com.iotekclass.buscompoents.model.permission.OperationResource;
import com.iotekclass.buscompoents.service.permission.ManageOperationRecordService;
import com.iotekclass.buscompoents.service.permission.OperationResourceService;
import com.iotekclass.common.constants.UserConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: OperationRecordInterceptor   
 * @Description： 操作记录拦截器
 * @Author：GuangChen
 * @Date：2015年5月14日 下午2:39:47
 * @version
 */
public class OperationRecordInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4296699681340704903L;
	
	private static Logger logger = LoggerFactory.getLogger(OperationRecordInterceptor.class);
	@Autowired
	OperationResourceService operationResourceService;
	@Autowired
	ManageOperationRecordService manageOperationRecordService;
	
	/** 
	 * @Description:
	 * @param invocation
	 * @return
	 * @throws Exception
	 * @throws 
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		//获取请求action
		String url = request.getRequestURI().trim().replace("/operation-consumer/", "/");
		
		//排除权限管理的action url
		if(!url.equals("/systemManage/validAccountPermission")){
			
			//通过url到资源表匹配对应操作
			OperationResource operationResource = operationResourceService.getByUrl(url);
			if(null!=operationResource){//判断资源表中是否存在记录，如果存在保存操作记录
				String params = "";//action参数
				Map parameterMap = request.getParameterMap();
				Set<String> keySets = parameterMap.keySet();
				for (String key : keySets) {
					 Object value = request.getParameter(key);
					 params += key+"="+value+",";//拼接参数用“,”隔开
				}
				ManageOperationRecord manageOperationRecord = new ManageOperationRecord();//准备操作记录
				manageOperationRecord.setUrl(url);
				
				//获取操作人信息
				HttpSession session = request.getSession();
				if (session != null) {
					ManageAccount manageAccount = (ManageAccount) session.getAttribute(UserConstants.LOGIN_USER_KEY);
					manageOperationRecord.setManageAccountId(manageAccount.getId());
				}
				manageOperationRecord.setModule(operationResource.getModule());
				manageOperationRecord.setParams(params);
				manageOperationRecord.setType(operationResource.getType());
				manageOperationRecord.setResult(1);
				manageOperationRecord.setDetail(operationResource.getDetail());
				manageOperationRecordService.save(manageOperationRecord);//保存操作记录
			}
		}
		return invocation.invoke();
	}

}
