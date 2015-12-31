/**
 * 
 */
package com.good;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.iotekclass.buscompoents.model.filter.UrlFilter;
import com.iotekclass.buscompoents.model.permission.ManageResource;
import com.iotekclass.buscompoents.model.permission.ManageRole;
import com.iotekclass.buscompoents.service.permission.ManageResourceService;
import com.iotekclass.buscompoents.service.permission.ManageRoleResourceRelService;
import com.iotekclass.buscompoents.service.permission.ManageRoleService;
import com.iotekclass.common.constants.UserConstants;
import com.iotekclass.common.util.StringUtil;
import com.iotekclass.components.service.cache.MemCacheService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description 过滤接口服务类实现
 * @author wangfengbao
 * @date 2015年4月29日 上午11:21:42
 *
 */
//@Service("filterService")
@Service(version = "1.0.0")
public class FilterServiceImpl implements FilterService {
	
	@Reference(version = "1.0.0")
	ManageRoleService manageRoleService;
	
	@Reference(version = "1.0.0")
	ManageResourceService manageResourceService;
	
	@Reference(version = "1.0.0")
	ManageRoleResourceRelService manageRoleResourceRelService;

	@Reference(version = "1.0.0")
	MemCacheService memCacheService;
	//private MemCachedManager cachedManager = MemCachedManager.getInstance();
	
//	@Autowired
//	ShiroFilerChainManager shiroFilerChainManager;
	
	public FilterServiceImpl() {

	}
	/** 
	 * @Description: 查询用户所具有的角色和权限 
	 * @param account
	 * @return
	 * @throws 
	*/ 
	@Override
	public List<UrlFilter> getAllFilters(int account) {
		
		List<UrlFilter> listFilters = new ArrayList<UrlFilter>();
		
		//获取账号对应的角色
		 List<ManageRole> roles = manageRoleService.getByAccountId(account);
		
		//获取角色资源信息
		for (int i = 0;roles!= null && i < roles.size(); i++) {
			
			//获取角色对应的所有的权限资源
			List<ManageResource> resources = manageResourceService.getByRoleId(roles.get(i).getId());
			
			//获取资源详细信息
			for (ManageResource manageResource: resources) {
				//处理相同权限，多个链接的情况
				if (!StringUtil.isEmpty(manageResource.getUrl())) {
					if (manageResource.getUrl().contains(",")) {
						for (String str: Arrays.asList(manageResource.getUrl().split(","))) {
							UrlFilter urlFilter  = new UrlFilter();
							
							//把角色信息添加到认证中
							urlFilter.setRoles(roles.get(i).getName());
							//把权限资源添加到认证中
							urlFilter.setUrl(str.trim());
							listFilters.add(urlFilter);
						}
					} else {
						
						UrlFilter urlFilter  = new UrlFilter();
						
						//把角色信息添加到认证中
						urlFilter.setRoles(roles.get(i).getName());
						//把权限资源添加到认证中
						urlFilter.setUrl(manageResource.getUrl().trim());
						listFilters.add(urlFilter);
					}
				}
			}
		}
		return listFilters;
	}
	
	/** 
	 * @Description: 更新过滤器中的角色及资源
	 * @param filters
	 * @throws 
	*/ 
	@Override
	public void updateFilters(List<UrlFilter> filters) {
		
//		shiroFilerChainManager.initFilterChains(filters); 
	}

	/**
	 * 
	 * @description 默认加载所有的角色和资源
	 * @author wangfengbao
	 * @date 2015年4月29日 下午2:40:25
	 *
	 */
	@PostConstruct 
	public void initFilterChain() { 
//	       shiroFilerChainManager.initFilterChains(getAllFilters(0)); 
	}

	/** 
	 * @Description: 更新用户过滤器中的角色及权限 (登录成功后调用)
	 * @param accountId
	 * @throws 
	*/ 
	@Override
	public void updateUserFilters(int accountId) {
//		shiroFilerChainManager.initFilterChains(getAllFilters(accountId));
	}

	/** 
	 * @Description: 校验url是否合法
	 * @param accountId
	 * @param url
	 * @return
	 * @throws 
	*/ 
	@SuppressWarnings("unchecked")
	@Override
	public boolean validUrl(int accountId, String url) {
		if (memCacheService.exists(accountId+UserConstants.ACCOUNT_PERMISSION_KEY)) {
			List<String> list = (List<String>)memCacheService.get(
					accountId+UserConstants.ACCOUNT_PERMISSION_KEY);
				return list.contains(url);
		}
		return false;
	}
	/** 
	 * @Description: 获取用户所有资源
	 * @param accountId
	 * @return
	 * @throws 
	*/ 
	@Override
	public List<String> getAllList(int accountId) {
		
		List<String> list = new ArrayList<String>();
		List<UrlFilter> urls = getAllFilters(accountId);
		for(UrlFilter urlFilter : urls) {
			list.add(urlFilter.getUrl());
		}
        return list;
	}
	/** 
	 * @Description: 获取角色所有资源
	 * @param role
	 * @return
	 * @throws 
	*/ 
	@Override
	public List<String> getAllListByRole(int role) {
		
		List<String> list = new ArrayList<String>();
		
		//获取角色对应的所有的权限资源
		List<ManageResource> resources = manageResourceService.getByRoleId(role);
		
		//获取资源详细信息
			for (ManageResource manageResource: resources) {
				//处理相同权限，多个链接的情况
				if (!StringUtil.isEmpty(manageResource.getUrl())) {
					if (manageResource.getUrl().contains(",")) {
						for (String str: Arrays.asList(manageResource.getUrl().split(","))) {
							list.add(str.trim());
						}
					} else {
						list.add(manageResource.getUrl());
					}
				}
		}
		return list;
	}
}
