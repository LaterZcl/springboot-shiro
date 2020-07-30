package com.later.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.later.domain.User;
import com.later.service.UserService;

/**
 * 自定义的Realm
 * @author zcl
 *
 */
public class UserRealm extends AuthorizingRealm{
	
	@Autowired UserService userService;

	/**
	 * 执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权逻辑");
		
		//给资源进行授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//通过SecurityUtils.getSubject().getPrincipal()获取用户对象
		//强转为User对象后，获取用户id
		Long userId = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
		
		//在数据库查找到授权字符串
		User user = userService.findById(userId);
		
		//添加资源的授权字符串
		//info.addStringPermission("user:add");
		info.addStringPermission(user.getPerms());
				
		return info;
	}

	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("执行认证逻辑");
		
		//数据库的用户和密码
		//String name = "zcl";
		//String pwd = "123456ss";
		
		
		//1.判断用户.
		UsernamePasswordToken UPtoken = (UsernamePasswordToken) token;
		User user = userService.findByName(UPtoken.getUsername());
		if(user==null) {
			//用户名不存在
			return null; // shiro底层会抛出UnknownAccountException
		}
		
		//2.判断密码
		return new SimpleAuthenticationInfo(user, user.getPassword(), "");
	}

}
