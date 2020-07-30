package com.later.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * Shiro的配置类
 * @author zcl
 */
@Configuration
public class ShiroConfig {

	/**
	 * 创建ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//添加Shiro内置过滤器
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 * 	常用的过滤器：
		 * 		anon：无需认证（登录）可以访问
		 * 		authc：必须认证才可以访问
		 * 		user：如果使用remenberMe的功能可以直接访问
		 * 		perms：该资源必须得到资源权限才可以访问
		 * 		role：该资源必须得到角色权限才可以访问
		 */
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		//拦截add.html页面和update.html页面需要登录才可以访问
		//filterChainDefinitionMap.put("/add", "authc");
		//filterChainDefinitionMap.put("/update", "authc");
		
		//对test.html页面放行
		filterChainDefinitionMap.put("/testThymeleaf", "anon");
		
		//对login.html页面放行
		filterChainDefinitionMap.put("/login", "anon");
		
		//授权过滤器
		//当授权拦截后shiro会自动跳转到未授权页面
		/**
		 * 多个时必须加上引号，并且参数之间用逗号分割
		 * 例如：/admins/user/**=perms["user:add:*,user:modify:*"]
		 * 当有多个参数时必须每个参数都通过才通过，相当于isPermitedAll()方法
		 */
		filterChainDefinitionMap.put("/add", "perms[user:add]");
		filterChainDefinitionMap.put("/update", "perms[user:update]");
		
		//使所有html页面都需要登录才可以访问
		filterChainDefinitionMap.put("/*", "authc");
		
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		//修改调整的登录页面
		shiroFilterFactoryBean.setLoginUrl("/toLogin");
		//设置提示未授权页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
		
		
		return shiroFilterFactoryBean;
	}
	
	/**
	 * 创建DefaultWebSecurityManager
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//关联Realm
		securityManager.setRealm(userRealm);
		return securityManager;
	}
	
	/**
	 * 创建Realm
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm() {
		return new UserRealm();
	}
	
	/**
	 * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
}
